package com.ss.parlour.userservice.handler.auth;

import com.ss.parlour.userservice.configurations.security.TokenProvider;
import com.ss.parlour.userservice.configurations.security.UserPrincipal;
import com.ss.parlour.userservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameMapper;
import com.ss.parlour.userservice.domain.cassandra.UserLoginEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.util.bean.*;
import com.ss.parlour.userservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.requests.EmailRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.userservice.util.common.TokenGenerator;
import com.ss.parlour.userservice.util.exception.UserRuntimeException;
import com.ss.parlour.userservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserDAOI userDAOI;

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${app.general.signup-email-verification}")
    private boolean signupEmailVerification;

    @Override
    public User loadUserByIdentification(String userIdentification){
        User user = null;
        AtomicReference<String> dbLoggedUserValue = new AtomicReference<>();
        Optional<UserLoginNameMapper> userLoginNameMapperFromDb = userDAOI.findUserByLoginName(userIdentification);
        userLoginNameMapperFromDb.ifPresentOrElse(
                currentUser -> {dbLoggedUserValue.set(currentUser.getUserId());},
                () ->{
                    Optional<UserLoginEmailMapper> loginNameEmailMapperFromDb = userDAOI.findUserByEmail(userIdentification);
                    loginNameEmailMapperFromDb.ifPresent(
                            userLoginNameEmailMapper -> {dbLoggedUserValue.set(userLoginNameEmailMapper.getUserId());}
                    );
                }
        );
        String userId = dbLoggedUserValue.get();
        if (userId != null){
            Optional<User> userFromDb = userDAOI.findUserByUserId(userId);
            user = userFromDb.orElseThrow(() -> new UserRuntimeException("User not found in db"));
        }
        return user;
    }

    @Override
    public UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean){
        UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean = userRegisterRequestBean.getUserRegisterRequestInnerBean();
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        boolean isEmailVerificationRequired = isEmailVerificationRequired(userRegisterRequestInnerBean);
        populateSignUpUserBean(userRegisterRequestInnerBean, isEmailVerificationRequired);
        if (isEmailVerificationRequired){ //If email verification required then generate token and send email
            signUpWithEmail(userRegisterRequestBean);
        }
        userRegistrationResponseBean.setActionType(userRegisterRequestInnerBean.getUserActionType());
        userRegistrationResponseBean.setEmailVerificationRequired(isEmailVerificationRequired);
        userRegistrationResponseBean.setEmail(userRegisterRequestInnerBean.getEmail());
        return userRegistrationResponseBean;
    }

    @Override
    public UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean = userRegisterRequestBean.getUserRegisterRequestInnerBean();
        User user = loadUserByIdentification(userRegisterRequestInnerBean.getEmail());
        if (user == null){
            boolean isEmailVerificationRequired = UserConst.TRUE;
            userRegisterRequestInnerBean.setLoginName(userRegisterRequestInnerBean.getEmail());
            populateSignUpUserBean(userRegisterRequestInnerBean, isEmailVerificationRequired);
        }
        userRegistrationTokenUpdateProcess(userRegisterRequestInnerBean);//Generate confirmation token
        requestForSignUpMail(userRegisterRequestInnerBean);//Send mail
        userRegistrationResponseBean.setActionType(userRegisterRequestInnerBean.getUserActionType());
        userRegistrationResponseBean.setEmailVerificationRequired(UserConst.TRUE);
        userRegistrationResponseBean.setEmail(userRegisterRequestInnerBean.getEmail());
        return userRegistrationResponseBean;
    }

    @Override
    public AuthResponseBean emailTokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        TokenConfirmRequest.TokenConfirmInnerRequest tokenConfirmInnerRequest = tokenConfirmRequest.getTokenConfirmInnerRequest();
        TokenConfirmResponseBean tokenConfirmResponseBean = new TokenConfirmResponseBean(UserConst.STATUS_SUCCESS, UserConst.SUCCESS_NARRATION);
        Optional<UserToken> existingUserToken = userDAOI.getUserToken(tokenConfirmInnerRequest.getUserIdentification(), tokenConfirmInnerRequest.getActionType());
        existingUserToken.ifPresent(currentToken -> {
            if (validateTokenExistence(tokenConfirmInnerRequest.getToken(), currentToken) && validateTokenExpiry(currentToken)){
                processTokenConfirmation(tokenConfirmInnerRequest);
                tokenConfirmResponseBean.setTokenConfirmSuccess(UserConst.TRUE);
            }
        });
        if (tokenConfirmResponseBean.isTokenConfirmSuccess()){ //If token validation successful
            //If token confirmation successful then generate token
            return tokenConfirmationTokenCreation(tokenConfirmInnerRequest);
        }
        throw new UserRuntimeException("Token confirmation failed");

    }

    @Override
    public User registerNewSocialUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        UserSignupHelperBean userSignupHelperBean = new UserSignupHelperBean();
        User user = new User(oAuth2UserRequest, oAuth2UserInfo);
        userSignupHelperBean.setUser(user);
        userSignupHelperBean.setUserLoginNameMapper(new UserLoginNameMapper(user));
        userSignupHelperBean.setUserLoginEmailMapper(new UserLoginEmailMapper(user));
        userDAOI.saveUserSignUpDataBeans(userSignupHelperBean);
        return user;
    }

    @Override
    public User updateExistingSocialUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userDAOI.saveUser(existingUser);
    }

    @Override
    public Map<String, String> createUserClaimMap(UserPrincipal userPrincipal){
        Map<String, String> claims = new HashMap<>();
        claims.put("username", userPrincipal.getUsername());
        String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("userId", userPrincipal.getUserId());
        claims.put("iss", "myApp");
        claims.put("scope", "message.read");
        return claims;
    }

    protected AuthResponseBean tokenConfirmationTokenCreation(TokenConfirmRequest.TokenConfirmInnerRequest tokenConfirmInnerRequest){
        AtomicReference<String> dbLoggedUserValue = new AtomicReference<>();
        Optional<UserLoginEmailMapper> loginNameEmailMapperFromDb = userDAOI.findUserByEmail(tokenConfirmInnerRequest.getUserIdentification());
        loginNameEmailMapperFromDb.ifPresent(loginEmailMapper -> {dbLoggedUserValue.set(loginEmailMapper.getUserId());});
        String userId = dbLoggedUserValue.get();
        Optional<User> userFromDb = userDAOI.findUserByUserId(userId);
        var user = userFromDb.orElseThrow(() -> new UserRuntimeException("User not found in db"));
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        Map<String, String> claimMap = createUserClaimMap(userPrincipal);
        String token = tokenProvider.createJwtForClaims(userPrincipal, claimMap);
        return AuthResponseBean.builder().accessToken(token).userId(userId).isAuthenticated(UserConst.TRUE).build();
    }

    protected void userRegistrationTokenUpdateProcess(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        UserToken userToken = populateUserToken(userRegisterRequestInnerBean.getEmail(), userRegisterRequestInnerBean.getUserActionType());
        userDAOI.saveUserToken(userToken);
    }

    protected EmailRequestBean populateSignUpEmailRequest(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        EmailRequestBean emailRequestBean = new EmailRequestBean();
        EmailRequestBean.EmailRequestInnerBean emailRequestInnerBean = emailRequestBean.new EmailRequestInnerBean();
        emailRequestInnerBean.setReceiverEmail(userRegisterRequestInnerBean.getEmail());
        emailRequestInnerBean.setActionType(userRegisterRequestInnerBean.getUserActionType());
        emailRequestInnerBean.setConfirmationToken(userRegisterRequestInnerBean.getToken());
        emailRequestBean.setEmailRequestInnerBean(emailRequestInnerBean);
        return emailRequestBean;
    }

    @Async
    protected void requestForSignUpMail(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        if (signupEmailVerification){ //If user registration successful then send registration success mail
            EmailRequestBean emailRequestBean = populateSignUpEmailRequest(userRegisterRequestInnerBean);
            externalRestWriterI.sendNotificationMail(emailRequestBean);
        }
    }

    protected boolean isEmailVerificationRequired(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        if ((userRegisterRequestInnerBean.getUserActionType().equals(UserConst.USER_ACTION_TYPE_REGISTER) && signupEmailVerification)){
            return UserConst.TRUE;
        }
        return UserConst.FALSE;
    }

    protected void populateSignUpUserBean(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean, boolean isEmailVerificationRequired){
        UserSignupHelperBean userSignupHelperBean = new UserSignupHelperBean();
        userRegisterRequestInnerBean.setUserId(tokenGenerator.generateUniqueUserId());
        userRegisterRequestInnerBean.setToken(UUID.randomUUID().toString());
        User user = new User(userRegisterRequestInnerBean, isEmailVerificationRequired);
        userSignupHelperBean.setUserLoginNameMapper(new UserLoginNameMapper(user));
        userSignupHelperBean.setUserLoginEmailMapper(new UserLoginEmailMapper(user));
        userSignupHelperBean.setUser(user);
        userDAOI.saveUserSignUpDataBeans(userSignupHelperBean);
    }

    protected UserToken populateUserToken(String userIdentification, String actionType){
        UserToken userToken = new UserToken();
        userToken.setUserIdentification(userIdentification);
        userToken.setActionType(actionType);
        userToken.setUserToken(tokenGenerator.generateLogicSecret());
        return userToken;
    }

    protected boolean validateTokenExistence(String inputToken, UserToken tokenInDb){
        if (inputToken != null && inputToken.equals(tokenInDb.getUserToken())){
            return UserConst.TRUE;
        }
        return UserConst.FALSE;
    }

    protected boolean validateTokenExpiry(UserToken userToken){
        return UserConst.TRUE;
    }

    protected void processTokenConfirmation(TokenConfirmRequest.TokenConfirmInnerRequest tokenConfirmInnerRequest){
        switch (tokenConfirmInnerRequest.getActionType()){
            case UserConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER -> System.out.println("Password less confirmation");
            case UserConst.USER_ACTION_TYPE_REGISTER -> System.out.println("Password confirmation");
            default -> System.out.println("");
        }
    }



}

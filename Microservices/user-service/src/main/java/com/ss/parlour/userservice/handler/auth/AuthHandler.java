package com.ss.parlour.userservice.handler.auth;

import com.ss.parlour.userservice.configurations.security.UserPrincipal;
import com.ss.parlour.userservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.util.bean.*;
import com.ss.parlour.userservice.util.bean.requests.EmailRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.userservice.util.common.TokenGenerator;
import com.ss.parlour.userservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Value("${app.general.signup-email-verification}")
    private boolean signupEmailVerification;

    @Override
    public User loadUserByIdentification(String userName){
        AtomicReference<User> dbLoggedUserValue = new AtomicReference<>();
        Optional<User> userFromDb = userDAOI.findUserByLoginName(userName);
        userFromDb.ifPresentOrElse(
                currentUser -> {dbLoggedUserValue.set(currentUser);},
                () ->{
                    Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb = userDAOI.findUserByEmail(userName);
                    loginNameEmailMapperFromDb.ifPresent(
                            userLoginNameEmailMapper -> {
                                String loginName = userLoginNameEmailMapper.getLoginName();
                                Optional<User> mappedUser = userDAOI.findUserByLoginName(loginName);
                                mappedUser.ifPresent(dbMappedUser -> {dbLoggedUserValue.set(dbMappedUser);;});
                            }
                    );
                }
        );
        return dbLoggedUserValue.get();
    }

    @Override
    public UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        UserSignupHelperBean userSignupHelperBean = new UserSignupHelperBean();
        populateSignUpUserBean(userSignupHelperBean, userRegisterRequestBean);
        populateUserLoginNameEmailMapper(userSignupHelperBean, userRegisterRequestBean);
        userDAOI.saveUserSignUpDataBeans(userSignupHelperBean);
        //Asynchronously send email requests + calling notification service
        requestForSignUpMail(userRegisterRequestBean);
        userRegistrationResponseBean.setNarration(UserConst.USER_REGISTER_SUCCESS_NARRATION);
        userRegistrationResponseBean.setStatus(UserConst.TRUE);
        userRegistrationResponseBean.setActionType(userRegisterRequestBean.getUserActionType());
        return userRegistrationResponseBean;
    }

    @Override
    public UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        UserToken userToken = populateUserToken(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getUserActionType());
        userDAOI.saveUserToken(userToken);
        requestForSignUpMail(userRegisterRequestBean);
        userRegistrationResponseBean.setNarration(UserConst.USER_REGISTER_SUCCESS_NARRATION);
        userRegistrationResponseBean.setStatus(UserConst.TRUE);
        userRegistrationResponseBean.setActionType(userRegisterRequestBean.getUserActionType());
        return userRegistrationResponseBean;
    }

    @Override
    public Map<String, String> createUserClaimMap(Authentication authentication){
        Map<String, String> claims = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        claims.put("username", userPrincipal.getUsername());
        String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("userId", userPrincipal.getEmail());
        claims.put("iss", "myApp");
        claims.put("scope", "message.read");
        return claims;
    }

    @Override
    public User registerNewSocialUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setLoginName(oAuth2UserInfo.getEmail());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setFirstName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return saveUser(user);
    }

    @Override
    public User updateExistingSocialUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return saveUser(existingUser);
    }

    @Override
    public User saveUser(User user){
        return userDAOI.saveUser(user);
    }

    @Override
    public TokenConfirmResponseBean tokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        TokenConfirmResponseBean tokenConfirmResponseBean = new TokenConfirmResponseBean(UserConst.STATUS_SUCCESS, UserConst.SUCCESS_NARRATION);
        Optional<UserToken> existingUserToken = userDAOI.getUserToken(tokenConfirmRequest.getUserName(), tokenConfirmRequest.getActionType());
        if (existingUserToken.isPresent()){
            UserToken userToken = existingUserToken.get();
            if (validateTokenExistence(userToken) && validateTokenExpiry(userToken)){
                processTokenConfirmation(tokenConfirmRequest);
                tokenConfirmResponseBean.setTokenConfirmSuccess(UserConst.TRUE);
            }
        }
        return tokenConfirmResponseBean;
    }

    protected EmailRequestBean populateSignUpEmailRequest(UserRegisterRequestBean userRegisterRequestBean){
        EmailRequestBean emailRequestBean = new EmailRequestBean();
        emailRequestBean.setReceiverEmail(userRegisterRequestBean.getEmail());
        emailRequestBean.setActionType(userRegisterRequestBean.getUserActionType());
        emailRequestBean.setConfirmationToken(userRegisterRequestBean.getToken());
        return emailRequestBean;
    }

    @Async
    protected void requestForSignUpMail(UserRegisterRequestBean userRegisterRequestBean){
        //If user registration successful then send registration success mail
        if (isEligibleToSendSignUpEmail(userRegisterRequestBean)){
            EmailRequestBean emailRequestBean = populateSignUpEmailRequest(userRegisterRequestBean);
            externalRestWriterI.sendNotificationMail(emailRequestBean);
        }
    }

    protected boolean isEligibleToSendSignUpEmail(UserRegisterRequestBean userRegisterRequestBean){
        if (userRegisterRequestBean.getUserActionType().equals(UserConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER)
                || (userRegisterRequestBean.getUserActionType().equals(UserConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER) && signupEmailVerification)){
            return UserConst.TRUE;
        }
        return UserConst.FALSE;
    }


    protected void populateUserLoginNameEmailMapper(UserSignupHelperBean userSignupHelperBean, UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper = populateLoginNameEmailMapper(userRegisterRequestBean);
        userSignupHelperBean.setLoginNameEmailMapper(loginNameEmailMapper);
    }

    protected UserLoginNameEmailMapper populateLoginNameEmailMapper(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper = new UserLoginNameEmailMapper();
        loginNameEmailMapper.setEmail(userRegisterRequestBean.getEmail());
        loginNameEmailMapper.setLoginName(userRegisterRequestBean.getEmail());
        return loginNameEmailMapper;
    }

    protected void populateSignUpUserBean(UserSignupHelperBean userSignupHelperBean, UserRegisterRequestBean userRegisterRequestBean){
        userRegisterRequestBean.setToken(UUID.randomUUID().toString());
        User user = populateUserForRegister(userRegisterRequestBean);
        userSignupHelperBean.setUser(user);
    }

    protected User populateUserForRegister(UserRegisterRequestBean userRegisterRequestBean){
        User user = new User();
        user.setFirstName(userRegisterRequestBean.getFirstName());
        user.setLastName(userRegisterRequestBean.getLastName());
        user.setLoginName(userRegisterRequestBean.getLoginName());
        user.setEmail(userRegisterRequestBean.getEmail());
        user.setPassword(userRegisterRequestBean.getPassword());
        user.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setEnabled(userRegisterRequestBean.getUserActionType().equals(UserConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER) ?
                UserConst.TRUE : UserConst.FALSE);
        user.setActiveToken(userRegisterRequestBean.getToken());
        user.setProvider(AuthProvider.local);
        return user;
    }

    protected UserToken populateUserToken(String userName, String actionType){
        UserToken userToken = new UserToken();
        userToken.setUserName(userName);
        userToken.setActionType(actionType);
        userToken.setUserToken(tokenGenerator.generateLogicSecret());
        return userToken;
    }

    protected boolean validateTokenExistence(UserToken userToken){
        if (userToken != null && userToken.equals(userToken.getUserToken())){
            return UserConst.TRUE;
        }
        return UserConst.FALSE;
    }

    protected boolean validateTokenExpiry(UserToken userToken){
        return UserConst.TRUE;
    }

    protected void processTokenConfirmation(TokenConfirmRequest tokenConfirmRequest){
        switch (tokenConfirmRequest.getActionType()){
            case UserConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER:
                //Do nothing for now
                break;
            case UserConst.USER_ACTION_TYPE_REGISTER:
                //Update user active status
                break;
            default:
                //Do nothing for now
        }
    }


}

package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.configurations.security.UserPrincipal;
import com.ss.parlour.authorizationservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.authorizationservice.dao.cassandra.UserDAOI;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.domain.cassandra.UserToken;
import com.ss.parlour.authorizationservice.util.bean.*;
import com.ss.parlour.authorizationservice.util.bean.requests.EmailRequestBean;
import com.ss.parlour.authorizationservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.common.TokenGenerator;
import com.ss.parlour.authorizationservice.util.exception.AuthorizationRuntimeException;
import com.ss.parlour.authorizationservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserDAOI userDAOI;

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    @Autowired
    private TokenGenerator tokenGenerator;

    public User loadUserByIdentification(String userName){
        return userDAOI.loadUserByIdentification(userName);
    }

    @Transactional
    @Override
    public void signUp(UserRegisterRequestBean userRegisterRequestBean){
        try {
            userDAOI.saveUserDetails(userRegisterRequestBean);
        }catch (Exception ex){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.USER_SAVE_ERROR);
        }
    }

    @Override
    public void signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        userDAOI.saveUserToken(userRegisterRequestBean, AuthorizationConst.USER_ACTION_TYPE_REGISTER);
        requestForMail(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getToken(), AuthorizationConst.USER_ACTION_TYPE_REGISTER);
    }

    @Override
    public void populateUserRegistrationResponseBean(UserRegistrationResponseBean userRegistrationResponseBean){
        userRegistrationResponseBean.setNarration(AuthorizationConst.USER_REGISTER_SUCCESS_NARRATION);
        userRegistrationResponseBean.setStatus(AuthorizationConst.TRUE);
    }

    @Override
    public EmailRequestBean populateEmailRequest(String receiverEmail, String token, String type){
        EmailRequestBean emailRequestBean = new EmailRequestBean();
        emailRequestBean.setReceiverEmail(receiverEmail);
        emailRequestBean.setActionType(type);
        emailRequestBean.setConfirmationToken(token);
        return emailRequestBean;
    }

    @Override
    public Map<String, String> createUserClaimMap(Authentication authentication){
        Map<String, String> claims = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        claims.put("username", userPrincipal.getUsername());
        String authorities = userPrincipal.getAuthorities().stream()
                                          .map(GrantedAuthority::getAuthority)
                                          .collect(Collectors.joining(","));
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

    @Async
    @Override
    public void requestForMail(String email, String token, String type){
        try {
            //If user registration successful then send registration success mail
            EmailRequestBean emailRequestBean = populateEmailRequest(email, token, type);
            externalRestWriterI.sendNotificationMail(emailRequestBean);
        }catch (Exception ex){
            //log exception + do not throw exception from here
        }
    }

    @Override
    public TokenConfirmResponseBean tokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        TokenConfirmResponseBean tokenConfirmResponseBean = new TokenConfirmResponseBean(AuthorizationConst.STATUS_SUCCESS, AuthorizationConst.SUCCESS_NARRATION);
        Optional<UserToken> existingUserToken = userDAOI.getUserToken(tokenConfirmRequest.getUserName(), tokenConfirmRequest.getActionType());
        if (existingUserToken.isPresent()){
            UserToken userToken = existingUserToken.get();
            if (validateTokenExistence(userToken) && validateTokenExpiry(userToken)){
                tokenConfirmResponseBean.setTokenConfirmSuccess(AuthorizationConst.TRUE);
            }
        }
        return tokenConfirmResponseBean;
    }

    protected boolean validateTokenExistence(UserToken userToken){
        if (userToken != null && userToken.equals(userToken.getUserToken())){
            return AuthorizationConst.TRUE;
        }
        return AuthorizationConst.FALSE;
    }

    protected boolean validateTokenExpiry(UserToken userToken){
        return AuthorizationConst.TRUE;
    }


}

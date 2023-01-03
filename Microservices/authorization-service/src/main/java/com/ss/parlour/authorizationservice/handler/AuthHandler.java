package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.configurations.security.UserPrincipal;
import com.ss.parlour.authorizationservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.authorizationservice.dao.UserDAOI;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.util.bean.*;
import com.ss.parlour.authorizationservice.util.bean.requests.EmailRequestBean;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.exception.AuthorizationRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserDAOI userDAOI;

    public User loadUserByIdentification(String userName){
        return userDAOI.loadUserByIdentification(userName);
    }

    @Transactional
    @Override
    public void createUser(UserRegisterRequestBean userRegisterRequestBean){
        try {
            userDAOI.saveUserDetails(userRegisterRequestBean);
        }catch (Exception ex){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.USER_SAVE_ERROR);
        }
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
        emailRequestBean.setType(type);
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


}

package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.util.bean.EmailRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRegistrationResponseBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

public interface AuthHandlerI {
    User loadUserByIdentification(String userName);
    void createUser(UserRegisterRequestBean userRegisterRequestBean);
    void populateUserRegistrationResponseBean(UserRegistrationResponseBean userRegistrationResponseBean);
    User saveUser(User user);
    EmailRequestBean populateEmailRequest(String receiverEmail, String token, String type);
    Map<String, String> createUserClaimMap(Authentication authentication);
    User registerNewSocialUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);
    User updateExistingSocialUser(User existingUser, OAuth2UserInfo oAuth2UserInfo);
}
package com.ss.parlour.userservice.handler.auth;

import com.ss.parlour.userservice.configurations.security.UserPrincipal;
import com.ss.parlour.userservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameMapper;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

public interface AuthHandlerI {
    User loadUserByIdentification(String userName);
    UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean);
    UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBeane);
    Map<String, String> createUserClaimMap(UserPrincipal userPrincipal);
    User registerNewSocialUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);
    User updateExistingSocialUser(User existingUser, OAuth2UserInfo oAuth2UserInfo);
    AuthResponseBean emailTokenConfirm(TokenConfirmRequest tokenConfirmRequest);

}

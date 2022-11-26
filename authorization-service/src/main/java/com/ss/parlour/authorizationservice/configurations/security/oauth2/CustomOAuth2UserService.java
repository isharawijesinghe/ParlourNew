package com.ss.parlour.authorizationservice.configurations.security.oauth2;

import com.ss.parlour.authorizationservice.configurations.security.UserPrincipal;
import com.ss.parlour.authorizationservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.authorizationservice.configurations.security.oauth2.user.OAuth2UserInfoFactory;
import com.ss.parlour.authorizationservice.dao.UserDAOI;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.handler.AuthHandlerI;
import com.ss.parlour.authorizationservice.util.bean.AuthProvider;
import com.ss.parlour.authorizationservice.util.exception.OAuth2AuthenticationProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AuthHandlerI authHandlerI;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        User user = authHandlerI.loadUserByIdentification(oAuth2UserInfo.getEmail());
        if(user != null) {
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = authHandlerI.updateExistingSocialUser(user, oAuth2UserInfo);
        } else {
            user = authHandlerI.registerNewSocialUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

}

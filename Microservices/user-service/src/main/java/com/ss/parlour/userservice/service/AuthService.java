package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.configurations.security.TokenProvider;
import com.ss.parlour.userservice.handler.auth.AuthHandlerI;
import com.ss.parlour.userservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.userservice.util.validators.AuthValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService implements AuthServiceI{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthValidatorI authValidatorI;

    @Autowired
    private AuthHandlerI authHandlerI;

    @Override
    public AuthResponseBean signIn(AuthRequestBean authRequestBean){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestBean.getUserIdentification(),
                        authRequestBean.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> claimMap = authHandlerI.createUserClaimMap(authentication);
        String token = tokenProvider.createJwtForClaims(authentication, claimMap);
        return new AuthResponseBean(token);
    }

    @Override
    public UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean){
        authValidatorI.validateSignUpRequest(userRegisterRequestBean);
        UserRegistrationResponseBean userRegistrationResponseBean = authHandlerI.signUp(userRegisterRequestBean);
        //Asynchronously send email requests + calling notification service
        authHandlerI.requestForMail(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getToken(), userRegisterRequestBean.getUserActionType());
        return userRegistrationResponseBean;
    }

    @Override
    public UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        authValidatorI.validateSignUpWithEmail(userRegisterRequestBean);
        UserRegistrationResponseBean userRegistrationResponseBean = authHandlerI.signUpWithEmail(userRegisterRequestBean);
        return userRegistrationResponseBean;
    }

    @Override
    public TokenConfirmResponseBean tokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        authValidatorI.validateTokenConfirm(tokenConfirmRequest);
        TokenConfirmResponseBean tokenConfirmResponseBean = authHandlerI.tokenConfirm(tokenConfirmRequest);
        return tokenConfirmResponseBean;
    }


}

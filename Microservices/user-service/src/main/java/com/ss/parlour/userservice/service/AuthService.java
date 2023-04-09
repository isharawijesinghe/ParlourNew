package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.configurations.security.TokenProvider;
import com.ss.parlour.userservice.configurations.security.UserPrincipal;
import com.ss.parlour.userservice.handler.auth.AuthHandlerI;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.common.UserResponse;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public UserResponse signIn(AuthRequestBean authRequestBean){
        AuthRequestBean.AuthRequestInnerBean authRequestInnerBean = authRequestBean.getAuthRequestInnerBean();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestInnerBean.getUserIdentification(),
                        authRequestInnerBean.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Map<String, String> claimMap = authHandlerI.createUserClaimMap(userPrincipal);
        String token = tokenProvider.createJwtForClaims(userPrincipal, claimMap);
        return  UserResponse.builder().body(new AuthResponseBean(userPrincipal.getUserId(), token))
                .userMsgHeader(authRequestBean.getUserMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(UserConst.USER_LOGIN_SUCCESS_NARRATION)
                .build();
    }

    @Override
    public UserResponse signUp(UserRegisterRequestBean userRegisterRequestBean){
        authValidatorI.validateSignUpRequest(userRegisterRequestBean);
        UserRegistrationResponseBean userRegistrationResponseBean = authHandlerI.signUp(userRegisterRequestBean);
        return  UserResponse.builder().body(userRegistrationResponseBean)
                .userMsgHeader(userRegisterRequestBean.getUserMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(UserConst.USER_REGISTER_SUCCESS_NARRATION)
                .build();
    }

    @Override
    public UserResponse signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        authValidatorI.validateSignUpWithEmail(userRegisterRequestBean);
        UserRegistrationResponseBean userRegistrationResponseBean = authHandlerI.signUpWithEmail(userRegisterRequestBean);
        return  UserResponse.builder().body(userRegistrationResponseBean)
                .userMsgHeader(userRegisterRequestBean.getUserMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(UserConst.USER_REGISTER_SUCCESS_NARRATION)
                .build();
    }

    @Override
    public UserResponse emailTokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        authValidatorI.validateTokenConfirm(tokenConfirmRequest);
        AuthResponseBean authResponseBean = authHandlerI.emailTokenConfirm(tokenConfirmRequest);
        return  UserResponse.builder().body(authResponseBean)
                .userMsgHeader(tokenConfirmRequest.getUserMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(UserConst.USER_REGISTER_TOKEN_CONFIRM_SUCCESS_NARRATION)
                .build();
    }


}

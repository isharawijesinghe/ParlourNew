package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.configurations.security.TokenProvider;
import com.ss.parlour.authorizationservice.configurations.security.UserPrincipal;
import com.ss.parlour.authorizationservice.handler.AuthHandlerI;
import com.ss.parlour.authorizationservice.util.bean.*;
import com.ss.parlour.authorizationservice.util.exception.AuthorizationRuntimeException;
import com.ss.parlour.authorizationservice.util.validators.AuthValidatorI;
import com.ss.parlour.authorizationservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    @Override
    public AuthResponseBean userLogin(AuthRequestBean authRequestBean){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestBean.getUserIdentification(),
                            authRequestBean.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.createToken(authentication);

            Map<String, String> claims = new HashMap<>();
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            claims.put("username", userPrincipal.getUsername());

            String authorities = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            claims.put("authorities", "ROLE_ADMIN");
            claims.put("userId", userPrincipal.getEmail());
            claims.put("iss", "myApp");
            claims.put("scope", "message.read");
            String token1 = tokenProvider.createJwtForClaims(userPrincipal.getUsername(), claims);


            return new AuthResponseBean(token1);
        }catch (AuthorizationRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public UserRegistrationResponseBean registerUser(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        try{
            authValidatorI.validateCreateUserRequest(userRegisterRequestBean);
            authHandlerI.createUser(userRegisterRequestBean);
            //Asynchronously send email requests + calling notification service
            requestForMail(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getToken(), AuthorizationConst.USER_ACTION_TYPE_REGISTER);
            authHandlerI.populateUserRegistrationResponseBean(userRegistrationResponseBean);
            return userRegistrationResponseBean;
        }catch (Exception ex){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Async
    @Override
    public void requestForMail(String email, String token, String type){
        try {
            //If user registration successful then send registration success mail
            EmailRequestBean emailRequestBean = authHandlerI.populateEmailRequest(email, token, type);
            externalRestWriterI.sendNotificationMail(emailRequestBean);
        }catch (Exception ex){
            //log exception + do not throw exception from here
        }
    }

}

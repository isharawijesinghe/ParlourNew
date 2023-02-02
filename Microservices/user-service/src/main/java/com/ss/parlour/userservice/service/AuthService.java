package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.configurations.security.TokenProvider;
import com.ss.parlour.userservice.handler.auth.AuthHandlerI;
import com.ss.parlour.userservice.util.bean.*;
import com.ss.parlour.userservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.userservice.util.exception.UserRuntimeException;
import com.ss.parlour.userservice.util.validators.AuthValidatorI;
import com.ss.parlour.userservice.writer.ExternalRestWriterI;
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

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    @Override
    public AuthResponseBean signIn(AuthRequestBean authRequestBean){
        try{
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
        }catch (UserRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new UserRuntimeException(UserErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        try{
            authValidatorI.validateSignUpRequest(userRegisterRequestBean);
            authHandlerI.signUp(userRegisterRequestBean);
            //Asynchronously send email requests + calling notification service
            authHandlerI.requestForMail(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getToken(), userRegisterRequestBean.getUserActionType());
            authHandlerI.populateUserRegistrationResponseBean(userRegistrationResponseBean);
            return userRegistrationResponseBean;
        }catch (UserRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            throw new UserRuntimeException(UserErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = new UserRegistrationResponseBean();
        try{
            authValidatorI.validateSignUpWithEmail(userRegisterRequestBean);
            authHandlerI.signUpWithEmail(userRegisterRequestBean);
            authHandlerI.populateUserRegistrationResponseBean(userRegistrationResponseBean);
            return userRegistrationResponseBean;
        }catch (UserRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            throw new UserRuntimeException(UserErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public TokenConfirmResponseBean tokenConfirm(TokenConfirmRequest tokenConfirmRequest){
        try{
            authValidatorI.validateTokenConfirm(tokenConfirmRequest);
            return authHandlerI.tokenConfirm(tokenConfirmRequest);
        }catch (UserRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new UserRuntimeException(UserErrorCodes.UNKNOWN_ERROR, ex);
        }
    }


}

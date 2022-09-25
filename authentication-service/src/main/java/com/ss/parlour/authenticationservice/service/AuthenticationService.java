package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.handler.AuthHandlerI;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;
import com.ss.parlour.authenticationservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthServiceI{

    @Autowired
    private AuthHandlerI authHandlerI;

    public AuthResponseBean login(AuthRequestBean authRequest){
        AuthResponseBean authResponseBean= new AuthResponseBean();
        User user= authHandlerI.login(authRequest);
        if (user != null){
            authResponseBean.setDescription("Temp auth success"); //todo implementation goes here
        }else {
            authResponseBean.setDescription("Temp auth failed"); //todo implementation goes here
        }
        return authResponseBean;
    }

    public AuthHandlerI getAuthHandlerI() {
        return authHandlerI;
    }

    public void setAuthHandlerI(AuthHandlerI authHandlerI) {
        this.authHandlerI = authHandlerI;
    }
}

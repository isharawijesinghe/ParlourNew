package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.handler.AuthHandlerI;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;
import com.ss.parlour.authenticationservice.util.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthServiceI{
    @Autowired
    private AuthHandlerI authHandlerI;


    public AuthResponseBean login(AuthRequestBean authRequest){
        UserBean user= authHandlerI.login(authRequest);
        AuthResponseBean authResponseBean= new AuthResponseBean();
        authResponseBean.setDescription("Temp auth success"); //todo implementation goes here
        return authResponseBean;
    }

    public AuthHandlerI getAuthHandlerI() {
        return authHandlerI;
    }

    public void setAuthHandlerI(AuthHandlerI authHandlerI) {
        this.authHandlerI = authHandlerI;
    }
}

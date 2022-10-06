package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.handler.AuthHandlerI;
import com.ss.parlour.authenticationservice.util.bean.*;
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
        if(user!=null) {
            authResponseBean.setStatus(Constants.STATUS_SUCCESS);
            authResponseBean.setDescription("Login success");
        }else{
            authResponseBean.setStatus(Constants.STATUS_SUCCESS);
            authResponseBean.setDescription("Invalid login name/password");
        }
        return authResponseBean;
    }

    @Override
    public UserResponseBean createUser(UserRequestBean userRequestBean) {
        return authHandlerI.createUser(userRequestBean);
    }

    @Override
    public UserResponseBean changePW(UserRequestBean userRequestBean) {
        return authHandlerI.changePW(userRequestBean);
    }

    public AuthHandlerI getAuthHandlerI() {
        return authHandlerI;
    }

    public void setAuthHandlerI(AuthHandlerI authHandlerI) {
        this.authHandlerI = authHandlerI;
    }
}

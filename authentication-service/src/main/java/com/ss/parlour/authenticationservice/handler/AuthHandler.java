package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.persistance.AuthDAO;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements AuthHandlerI {
    @Autowired
    private AuthDAO authDAO;

    public UserBean login(AuthRequestBean authRequestBean){
        return authDAO.login(authRequestBean);
    }

    public void createUser(UserBean userBeanBean){
//        return authDAO.createUser(authRequestBean);
    }

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }
}

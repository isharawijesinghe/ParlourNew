package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.persistance.AuthDAO;
import com.ss.parlour.authenticationservice.repository.;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserBean;
import com.ss.parlour.authenticationservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements AuthHandlerI {
    @Autowired
    private AuthDAO authDAO;
    @Autowired
    private  ;

    public User login(AuthRequestBean authRequestBean){
        User user = .findByLoginName(authRequestBean.getLoginName());
        return user;
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

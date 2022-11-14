package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.dao.UserDAOI;
import com.ss.parlour.authorizationservice.repository.UserRepositoryI;
import com.ss.parlour.authorizationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authorizationservice.domain.User;
import com.ss.parlour.authorizationservice.util.bean.Constants;
import com.ss.parlour.authorizationservice.util.bean.UserRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserDAOI userDAOI;

    public User getUserByUserName(String userName){
        return userDAOI.getUser(userName);
    }


}

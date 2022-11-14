package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authorizationservice.domain.User;
import com.ss.parlour.authorizationservice.util.bean.UserRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserResponseBean;

public interface AuthHandlerI {
    public User getUserByUserName(String userName);
}

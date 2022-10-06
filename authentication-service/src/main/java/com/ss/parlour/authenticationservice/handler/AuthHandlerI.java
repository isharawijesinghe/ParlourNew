package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.domain.User;
import com.ss.parlour.authenticationservice.util.bean.UserRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserResponseBean;

public interface AuthHandlerI {
    public User login(AuthRequestBean authRequestBean);
    public UserResponseBean createUser(UserRequestBean userRequestBean);
    public UserResponseBean changePW(UserRequestBean userRequestBean);
}

package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.util.bean.AuthRequestBean;
import com.ss.parlour.userservice.domain.User;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;

public interface AuthHandlerI {
    public User login(AuthRequestBean authRequestBean);
    public UserResponseBean createUser(UserRequestBean userRequestBean);
    public UserResponseBean changePW(UserRequestBean userRequestBean);
    public User getUserByUserName(String userName);
}

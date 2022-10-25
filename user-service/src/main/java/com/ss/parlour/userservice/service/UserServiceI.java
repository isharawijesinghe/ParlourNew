package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;

public interface UserServiceI {

    UserResponseBean createUser(UserRequestBean userRequestBean);
    UserResponseBean changePW(UserRequestBean userRequestBean);
}

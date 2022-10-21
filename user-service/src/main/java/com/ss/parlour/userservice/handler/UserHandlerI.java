package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.util.bean.AuthRequestBean;
import com.ss.parlour.userservice.domain.User;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;

public interface UserHandlerI {

    public UserResponseBean createUser(UserRequestBean userRequestBean);
    public UserResponseBean changePW(UserRequestBean userRequestBean);
}

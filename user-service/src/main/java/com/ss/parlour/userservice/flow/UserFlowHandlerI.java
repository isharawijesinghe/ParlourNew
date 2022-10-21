package com.ss.parlour.userservice.flow;

import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;

public interface UserFlowHandlerI {

    UserResponseBean createUser(UserRequestBean userRequestBean);
    UserResponseBean changePW(UserRequestBean userRequestBean);
}

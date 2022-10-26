package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.UserHandlerI;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;
import com.ss.parlour.userservice.util.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserServiceI{

    @Autowired
    UserHandlerI userHandlerI;

    @Autowired
    UserValidatorI userValidatorI;

    @Override
    public UserResponseBean createUser(UserRequestBean userRequestBean) {
        UserResponseBean userResponseBean = new UserResponseBean();
        userValidatorI.validateCreateUserRequest(userRequestBean);
        return userResponseBean;
    }

    @Override
    public UserResponseBean changePW(UserRequestBean userRequestBean) {
        UserResponseBean userResponseBean = new UserResponseBean();
        userValidatorI.validateChangePwRequest(userRequestBean);
        return userResponseBean;
    }
}

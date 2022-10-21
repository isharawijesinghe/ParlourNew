package com.ss.parlour.userservice.flow;

import com.ss.parlour.userservice.domain.User;
import com.ss.parlour.userservice.handler.UserHandlerI;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;
import com.ss.parlour.userservice.util.exception.UserServiceRequestException;
import com.ss.parlour.userservice.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFlowHandler implements UserFlowHandlerI{

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
        try {
            userValidatorI.validateChangePwRequest(userRequestBean);
        }catch (Exception ex){

        }
        return null;
    }
}

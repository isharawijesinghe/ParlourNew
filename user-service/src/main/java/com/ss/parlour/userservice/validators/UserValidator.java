package com.ss.parlour.userservice.validators;

import com.ss.parlour.userservice.util.bean.UserRequestBean;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements UserValidatorI{

    @Override
    public void validateCreateUserRequest(UserRequestBean userRequestBean) {

    }

    @Override
    public void validateChangePwRequest(UserRequestBean userRequestBean) {

    }
}

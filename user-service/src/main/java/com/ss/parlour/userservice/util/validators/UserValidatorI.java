package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.util.bean.UserRequestBean;

public interface UserValidatorI {

    void validateCreateUserRequest(UserRequestBean userRequestBean);
    void validateChangePwRequest(UserRequestBean userRequestBean);
}

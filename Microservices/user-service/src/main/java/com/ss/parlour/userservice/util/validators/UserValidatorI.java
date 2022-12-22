package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;

public interface UserValidatorI {

    void validateCreateUserRequest(UserRegisterRequestBean userRegisterRequestBean);
    void validateChangePwRequest(UserRegisterRequestBean userRegisterRequestBean);
}

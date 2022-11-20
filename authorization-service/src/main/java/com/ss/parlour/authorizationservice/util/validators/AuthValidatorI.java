package com.ss.parlour.authorizationservice.util.validators;

import com.ss.parlour.authorizationservice.util.bean.UserRegisterRequestBean;

public interface AuthValidatorI {

    void validateCreateUserRequest(UserRegisterRequestBean userRegisterRequestBean);
}

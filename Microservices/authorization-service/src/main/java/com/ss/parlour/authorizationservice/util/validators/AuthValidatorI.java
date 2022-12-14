package com.ss.parlour.authorizationservice.util.validators;

import com.ss.parlour.authorizationservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;

public interface AuthValidatorI {

    void validateSignUpRequest(UserRegisterRequestBean userRegisterRequestBean);
    void validateSignUpWithEmail(UserRegisterRequestBean userRegisterRequestBean);
    void validateTokenConfirm(TokenConfirmRequest tokenConfirmRequest);
}

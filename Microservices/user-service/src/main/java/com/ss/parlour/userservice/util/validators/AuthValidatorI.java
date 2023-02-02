package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;

public interface AuthValidatorI {

    void validateSignUpRequest(UserRegisterRequestBean userRegisterRequestBean);
    void validateSignUpWithEmail(UserRegisterRequestBean userRegisterRequestBean);
    void validateTokenConfirm(TokenConfirmRequest tokenConfirmRequest);

}

package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.common.UserResponse;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.userservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;

public interface AuthServiceI  {

    UserResponse signIn(AuthRequestBean authRequestBean);
    UserResponse signUp(UserRegisterRequestBean userRegisterRequestBean);
    UserResponse signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean);
    UserResponse emailTokenConfirm(TokenConfirmRequest tokenConfirmRequest);

}

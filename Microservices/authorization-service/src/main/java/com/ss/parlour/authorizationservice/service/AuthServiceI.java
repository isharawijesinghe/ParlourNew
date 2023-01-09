package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.AuthResponseBean;

public interface AuthServiceI  {

    AuthResponseBean signIn(AuthRequestBean authRequestBean);
    UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean);
    UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean);
}

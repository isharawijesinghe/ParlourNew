package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.util.bean.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.AuthResponseBean;

public interface AuthServiceI  {

    AuthResponseBean userLogin(AuthRequestBean authRequestBean);
    UserRegistrationResponseBean registerUser(UserRegisterRequestBean userRegisterRequestBean);
    void requestForMail(String email, String token, String type);
}

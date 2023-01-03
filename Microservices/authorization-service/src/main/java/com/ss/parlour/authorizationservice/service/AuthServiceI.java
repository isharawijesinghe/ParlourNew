package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.AuthResponseBean;

public interface AuthServiceI  {

    AuthResponseBean userLogin(AuthRequestBean authRequestBean);
    UserRegistrationResponseBean registerUser(UserRegisterRequestBean userRegisterRequestBean);
    void requestForMail(String email, String token, String type);
}

package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.authorizationservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.authorizationservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import com.ss.parlour.authorizationservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.AuthResponseBean;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthServiceI  {

    AuthResponseBean signIn(AuthRequestBean authRequestBean);
    UserRegistrationResponseBean signUp(UserRegisterRequestBean userRegisterRequestBean);
    UserRegistrationResponseBean signUpWithEmail(UserRegisterRequestBean userRegisterRequestBean);
    TokenConfirmResponseBean tokenConfirm(TokenConfirmRequest tokenConfirmRequest);
    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean generatePreSignUrl);
}

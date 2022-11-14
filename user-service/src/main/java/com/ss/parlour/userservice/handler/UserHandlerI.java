package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.util.bean.EmailRequestBean;
import com.ss.parlour.userservice.util.bean.UserCommonResponseBean;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;

public interface UserHandlerI {

    void createUser(UserRegisterRequestBean userRegisterRequestBean);
    UserCommonResponseBean changePW(UserRegisterRequestBean userRegisterRequestBean);
    void populateUserCreateResponseBean(UserCommonResponseBean userCommonResponseBean);
    UserCommonResponseBean populateUserChangePwResponseBean(UserRegisterRequestBean userRegisterRequestBean);
    EmailRequestBean populateEmailRequest(String receiverEmail, String token, String type);
    void sendNotificationMail(EmailRequestBean emailRequestBean);
    void processConfirmation(String token, String type);
}

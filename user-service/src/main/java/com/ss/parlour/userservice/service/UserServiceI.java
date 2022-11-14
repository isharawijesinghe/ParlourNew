package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.UserCommonResponseBean;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserServiceI {

    UserCommonResponseBean registerUser(UserRegisterRequestBean userRegisterRequestBean);
    UserCommonResponseBean changePW(UserRegisterRequestBean userRegisterRequestBean);
    void requestForMail(String email, String token, String type);
    UserCommonResponseBean confirm(String token, String type);
}

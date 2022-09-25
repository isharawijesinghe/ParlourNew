package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.domain.User;

public interface AuthHandlerI {
    public User login(AuthRequestBean authRequestBean);
}

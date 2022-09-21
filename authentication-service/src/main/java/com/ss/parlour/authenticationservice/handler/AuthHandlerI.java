package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserBean;

public interface AuthHandlerI {
    public UserBean login(AuthRequestBean authRequestBean);
}

package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;

public interface AuthServiceI {
    public AuthResponseBean login(AuthRequestBean authRequest);
}

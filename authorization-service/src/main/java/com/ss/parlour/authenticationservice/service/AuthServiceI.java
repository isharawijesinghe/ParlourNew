package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;
import com.ss.parlour.authenticationservice.util.bean.UserRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserResponseBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthServiceI  {
    public AuthResponseBean login(AuthRequestBean authRequest);
    public UserResponseBean createUser(UserRequestBean userRequestBean);
    public UserResponseBean changePW(UserRequestBean userRequestBean);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

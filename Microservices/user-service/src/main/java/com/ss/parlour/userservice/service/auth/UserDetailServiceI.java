package com.ss.parlour.userservice.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailServiceI {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

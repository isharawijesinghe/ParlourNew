package com.ss.parlour.authenticationservice.service;

import com.ss.parlour.authenticationservice.domain.User;
import com.ss.parlour.authenticationservice.handler.AuthHandlerI;
import com.ss.parlour.authenticationservice.util.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailService implements AuthServiceI, UserDetailsService {

    @Autowired
    private AuthHandlerI authHandlerI;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= authHandlerI.getUserByUserName(username);
        if (user == null){
            throw  new UsernameNotFoundException("No User Found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getDefaultEmail(),
                user.getLoginPw(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(List.of(user.getRole()))
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    public AuthResponseBean login(AuthRequestBean authRequest){
        AuthResponseBean authResponseBean= new AuthResponseBean();
        User user= authHandlerI.login(authRequest);
        if(user!=null) {
            authResponseBean.setStatus(Constants.STATUS_SUCCESS);
            authResponseBean.setDescription("Login success");
        }else{
            authResponseBean.setStatus(Constants.STATUS_SUCCESS);
            authResponseBean.setDescription("Invalid login name/password");
        }
        return authResponseBean;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public UserResponseBean createUser(UserRequestBean userRequestBean) {
        return authHandlerI.createUser(userRequestBean);
    }

    @Override
    public UserResponseBean changePW(UserRequestBean userRequestBean) {
        return authHandlerI.changePW(userRequestBean);
    }

    public AuthHandlerI getAuthHandlerI() {
        return authHandlerI;
    }

    public void setAuthHandlerI(AuthHandlerI authHandlerI) {
        this.authHandlerI = authHandlerI;
    }

}

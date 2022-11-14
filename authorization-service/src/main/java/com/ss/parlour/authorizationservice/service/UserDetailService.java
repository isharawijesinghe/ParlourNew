package com.ss.parlour.authorizationservice.service;

import com.ss.parlour.authorizationservice.domain.User;
import com.ss.parlour.authorizationservice.handler.AuthHandlerI;
import com.ss.parlour.authorizationservice.util.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
              //  getAuthorities(user.getRole() != null ? List.of(user.getRole()): new ArrayList<>())
                getAuthorities(new ArrayList<>())
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        if(roles != null){
            for(String role: roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }


    public AuthHandlerI getAuthHandlerI() {
        return authHandlerI;
    }

    public void setAuthHandlerI(AuthHandlerI authHandlerI) {
        this.authHandlerI = authHandlerI;
    }

}

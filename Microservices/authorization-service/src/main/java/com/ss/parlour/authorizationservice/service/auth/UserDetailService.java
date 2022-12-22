package com.ss.parlour.authorizationservice.service.auth;

import com.ss.parlour.authorizationservice.configurations.security.UserPrincipal;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.handler.AuthHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailService implements UserDetailsService, UserDetailServiceI {

    @Autowired
    private AuthHandlerI authHandlerI;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= authHandlerI.loadUserByIdentification(username);
        if (user == null){
            throw  new UsernameNotFoundException("No User Found");
        }
        return UserPrincipal.create(user);
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

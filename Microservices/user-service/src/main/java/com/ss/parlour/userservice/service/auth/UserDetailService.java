package com.ss.parlour.userservice.service.auth;

import com.ss.parlour.userservice.configurations.security.UserPrincipal;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameMapper;
import com.ss.parlour.userservice.handler.auth.AuthHandlerI;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.exception.UserRuntimeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Getter
@Setter
@NoArgsConstructor
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
        } else if (user.getEnabled() != UserConst.USER_ENABLE){
            throw  new UserRuntimeException("User disabled. Verify user ");
        }
        return UserPrincipal.create(user);
    }

    protected Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        if(roles != null){
            for(String role: roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }


}

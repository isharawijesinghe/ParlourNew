package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.util.bean.EmailRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRegistrationResponseBean;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthHandlerI {
    User loadUserByIdentification(String userName);
    void createUser(UserRegisterRequestBean userRegisterRequestBean);
    void populateUserRegistrationResponseBean(UserRegistrationResponseBean userRegistrationResponseBean);
    User saveUser(User user);
    EmailRequestBean populateEmailRequest(String receiverEmail, String token, String type);
    Map<String, String> createUserClaimMap(Authentication authentication);
}

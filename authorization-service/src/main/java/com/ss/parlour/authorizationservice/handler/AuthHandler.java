package com.ss.parlour.authorizationservice.handler;

import com.ss.parlour.authorizationservice.dao.UserDAOI;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.util.bean.*;
import com.ss.parlour.authorizationservice.util.exception.AuthorizationRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserDAOI userDAOI;

    public User loadUserByIdentification(String userName){
        return userDAOI.loadUserByIdentification(userName);
    }

    @Transactional
    @Override
    public void createUser(UserRegisterRequestBean userRegisterRequestBean){
        try {
            userDAOI.saveUserDetails(userRegisterRequestBean);
        }catch (Exception ex){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.USER_SAVE_ERROR);
        }
    }

    @Override
    public void populateUserRegistrationResponseBean(UserRegistrationResponseBean userRegistrationResponseBean){
        userRegistrationResponseBean.setNarration(AuthorizationConst.USER_REGISTER_SUCCESS_NARRATION);
        userRegistrationResponseBean.setStatus(AuthorizationConst.TRUE);
    }

    @Override
    public EmailRequestBean populateEmailRequest(String receiverEmail,  String token, String type){
        EmailRequestBean emailRequestBean = new EmailRequestBean();
        emailRequestBean.setReceiverEmail(receiverEmail);
        emailRequestBean.setType(type);
        emailRequestBean.setConfirmationToken(token);
        return emailRequestBean;
    }



    @Override
    public User saveUser(User user){
        return userDAOI.saveUser(user);
    }


}

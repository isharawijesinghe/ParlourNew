package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.UserHandlerI;
import com.ss.parlour.userservice.util.bean.*;
import com.ss.parlour.userservice.util.exception.UserServiceRuntimeException;
import com.ss.parlour.userservice.util.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class UserService implements UserServiceI{

    @Autowired
    UserHandlerI userHandlerI;

    @Autowired
    UserValidatorI userValidatorI;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserCommonResponseBean registerUser(UserRegisterRequestBean userRegisterRequestBean)  {
        UserCommonResponseBean userCommonResponseBean = new UserCommonResponseBean();
        try{
            userValidatorI.validateCreateUserRequest(userRegisterRequestBean);
            userHandlerI.createUser(userRegisterRequestBean);
            //Asynchronously send email requests + calling notification service
            requestForMail(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getToken(), UserConst.USER_ACTION_TYPE_REGISTER);
            userHandlerI.populateUserCreateResponseBean(userCommonResponseBean);
            return userCommonResponseBean;
        }catch (UserServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new UserServiceRuntimeException(UserErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    public void confirmRegistration(){
        try{

        }catch (UserServiceRuntimeException ex){

        }catch (Exception ex){

        }
    }

    @Override
    public UserCommonResponseBean changePW(UserRegisterRequestBean userRegisterRequestBean) {
        try{
            UserCommonResponseBean userCommonResponseBean = new UserCommonResponseBean();
            userValidatorI.validateChangePwRequest(userRegisterRequestBean);
            userHandlerI.changePW(userRegisterRequestBean);
            userHandlerI.populateUserChangePwResponseBean(userRegisterRequestBean);
            return userCommonResponseBean;
        }catch (UserServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new UserServiceRuntimeException(UserErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    public UserCommonResponseBean forgetPw(UserRegisterRequestBean userRegisterRequestBean){
        return null;
    }

    @Async
    @Override
    public void requestForMail(String email, String token, String type){
        try {
            //If user registration successful then send registration success mail
            EmailRequestBean emailRequestBean = userHandlerI.populateEmailRequest(email, token, type);
            userHandlerI.sendNotificationMail(emailRequestBean);
        }catch (Exception ex){
            //log exception + do not throw exception from here
        }
    }

    @Override
    public UserCommonResponseBean confirm(String token, String type){
        try{
            UserCommonResponseBean userCommonResponseBean = new UserCommonResponseBean();

            return userCommonResponseBean;
        }catch (UserServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new UserServiceRuntimeException(UserErrorCodes.UNKNOWN_ERROR , ex);
        }
    }
}

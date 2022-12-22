package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.dao.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.util.bean.*;
import com.ss.parlour.userservice.util.exception.UserServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserHandler implements UserHandlerI {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDAOI userDAOI;

    public void createUser(UserRegisterRequestBean userRegisterRequestBean){
        try {
            userDAOI.saveUserDetails(userRegisterRequestBean);
        }catch (Exception ex){
            throw new UserServiceRuntimeException(UserErrorCodes.USER_SAVE_ERROR);
        }
    }

    @Override
    public UserCommonResponseBean changePW(UserRegisterRequestBean userRegisterRequestBean) {
        UserCommonResponseBean userCommonResponseBean =new UserCommonResponseBean();
//        User user = userRepositoryI.findByLoginName(userRegisterRequestBean.getLoginName());
//        if(user!=null && userRegisterRequestBean.getPw()!=null && userRegisterRequestBean.getPw().length()>0){
//            if(userRegisterRequestBean.getOldPW()!=null && userRegisterRequestBean.getOldPW().toUpperCase().trim().equals(user.getLoginPw().toUpperCase())) {
//                user.setLoginPw(userRegisterRequestBean.getPw());
//                userRepositoryI.save(user);
//                userCommonResponseBean.setStatus(UserConst.STATUS_SUCCESS);
//                userCommonResponseBean.setNarration("Password has changed successfully");
//                return userCommonResponseBean;
//            }
//        }
//        userCommonResponseBean.setNarration("Invalid login name or password");
//        userCommonResponseBean.setStatus(UserConst.STATUS_FAILED);

        return userCommonResponseBean;
    }

    @Override
    public void processConfirmation(String token, String type){
        User user = userDAOI.getUserByTokenAndType(token, type);
        if (user == null){

        }
        user.setEnabled(true);

    }

    public EmailRequestBean populateEmailRequest(String receiverEmail,  String token, String type){
        EmailRequestBean emailRequestBean = new EmailRequestBean();
        emailRequestBean.setReceiverEmail(receiverEmail);
        emailRequestBean.setType(type);
        emailRequestBean.setConfirmationToken(token);
        return emailRequestBean;
    }

    public void sendNotificationMail(EmailRequestBean emailRequestBean){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailRequestBean> requestEntity = new HttpEntity<>(emailRequestBean, requestHeaders);

        restTemplate.exchange("http://127.0.0.1:9005/email/sendEmail",
                HttpMethod.POST,
                requestEntity,
                EmailResponseBean.class
        );
    }

    public void populateUserCreateResponseBean(UserCommonResponseBean userCommonResponseBean){
        userCommonResponseBean.setStatus(UserConst.STATUS_SUCCESS);
        userCommonResponseBean.setNarration(UserConst.SUCCESS_NARRATION);
    }

    public UserCommonResponseBean populateUserChangePwResponseBean(UserRegisterRequestBean userRegisterRequestBean){
        return null;
    }

}

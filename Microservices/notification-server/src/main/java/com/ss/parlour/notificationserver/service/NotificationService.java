package com.ss.parlour.notificationserver.service;

import com.ss.parlour.notificationserver.utils.bean.NotificationConst;
import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.handlers.NotificationHandlerI;
import com.ss.parlour.notificationserver.handlers.NotificationSenderFactoryI;
import com.ss.parlour.notificationserver.handlers.email.EmailServiceHandlerI;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import com.ss.parlour.notificationserver.utils.exception.NotificationServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class NotificationService implements NotificationServiceI {

    @Autowired
    private NotificationHandlerI notificationHandlerI;

    @Override
    public EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean){
        try{
            return notificationHandlerI.sendEmailRequest(emailRequestBean);
        } catch (NotificationServiceRuntimeException ex){
            throw ex;
        } catch (Exception ex){
            throw new NotificationServiceRuntimeException(NotificationConst.UNKNOWN_ERROR, ex);
        }
    }
}

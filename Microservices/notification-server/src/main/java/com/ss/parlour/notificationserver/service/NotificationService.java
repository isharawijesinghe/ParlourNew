package com.ss.parlour.notificationserver.service;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.handlers.NotificationHandlerI;
import com.ss.parlour.notificationserver.handlers.NotificationSenderFactoryI;
import com.ss.parlour.notificationserver.handlers.email.EmailServiceHandlerI;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class NotificationService implements NotificationServiceI {

    @Autowired
    private NotificationSenderFactoryI notificationSenderFactoryI;

    @Value("${application.emailProvide}")
    private String emailProvider;

    private JavaMailSender mailSender;

    @Autowired
    private NotificationHandlerI notificationHandlerI;


    @Autowired
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean){
        try{
            return notificationHandlerI.sendEmailRequest(emailRequestBean);
        }catch (Exception ex){
            throw ex;
        }
    }
}

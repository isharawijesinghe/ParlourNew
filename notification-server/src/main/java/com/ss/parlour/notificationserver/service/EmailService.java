package com.ss.parlour.notificationserver.service;

import com.ss.parlour.notificationserver.bean.EmailRequestBean;
import com.ss.parlour.notificationserver.handlers.EmailServiceHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService implements EmailServiceI {

    @Autowired
    EmailServiceHandlerI emailServiceHandlerI;

    private JavaMailSender mailSender;


    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailRequestBean emailRequestBean){
        SimpleMailMessage simpleMailMessage = emailServiceHandlerI.createSimpleMailRequest(emailRequestBean);
        mailSender.send(simpleMailMessage);
    }
}

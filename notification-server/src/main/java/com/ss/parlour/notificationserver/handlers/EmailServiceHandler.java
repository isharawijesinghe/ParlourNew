package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.bean.EmailRequestBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceHandler implements EmailServiceHandlerI {

    String appUrl = "http://127.0.0.1:9003";

    public SimpleMailMessage createSimpleMailRequest(EmailRequestBean emailRequestBean){
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(emailRequestBean.getReceiverEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + emailRequestBean.getConfirmationToken());
        registrationEmail.setFrom("noreply@domain.com");
        return registrationEmail;
    }
}

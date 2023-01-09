package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.handlers.email.EmailServiceHandlerI;
import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandler implements NotificationHandlerI {

    @Autowired
    private NotificationSenderFactoryI notificationSenderFactoryI;

    @Value("${application.emailProvide}")
    private String emailProvider;

    @Override
    public EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean){
        EmailServiceHandlerI emailServiceHandlerI = notificationSenderFactoryI.getEmailServiceHandlerI(emailProvider);
        return emailServiceHandlerI.sendEmailRequest(emailRequestBean);
    }
}

package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.handlers.email.EmailServiceHandlerI;
import com.ss.parlour.notificationserver.handlers.email.GoogleEmailServiceHandler;
import com.ss.parlour.notificationserver.handlers.email.SNSEmailHandler;
import com.ss.parlour.notificationserver.utils.bean.NotificationConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationSenderFactory implements NotificationSenderFactoryI{

    @Autowired
    private GoogleEmailServiceHandler googleEmailServiceHandler;

    @Autowired
    private SNSEmailHandler snsEmailHandler;

    @Override
    public EmailServiceHandlerI getEmailServiceHandlerI(String emailType){
        if (emailType.equals(NotificationConst.EMAIL_TYPE_GOOGLE )){
            return googleEmailServiceHandler;
        } else if (emailType.equals(NotificationConst.EMAIL_TYPE_SNS)){
            return snsEmailHandler;
        } else {
            return null;
        }
    }
}

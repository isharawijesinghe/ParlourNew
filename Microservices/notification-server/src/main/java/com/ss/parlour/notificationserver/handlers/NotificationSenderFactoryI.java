package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.handlers.email.EmailServiceHandlerI;

public interface NotificationSenderFactoryI {

    EmailServiceHandlerI getEmailServiceHandlerI(String emailType);
}

package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.bean.EmailRequestBean;
import org.springframework.mail.SimpleMailMessage;

public interface EmailServiceHandlerI {

    SimpleMailMessage createSimpleMailRequest(EmailRequestBean emailRequestBean);
}

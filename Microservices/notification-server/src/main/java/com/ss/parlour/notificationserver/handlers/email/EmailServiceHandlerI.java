package com.ss.parlour.notificationserver.handlers.email;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.springframework.mail.SimpleMailMessage;

public interface EmailServiceHandlerI {

    EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean);
}

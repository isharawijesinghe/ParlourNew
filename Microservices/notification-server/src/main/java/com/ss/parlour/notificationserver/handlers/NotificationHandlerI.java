package com.ss.parlour.notificationserver.handlers;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;

public interface NotificationHandlerI {

    EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean);
}

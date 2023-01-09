package com.ss.parlour.notificationserver.service;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;

public interface NotificationServiceI {

    EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean);
}

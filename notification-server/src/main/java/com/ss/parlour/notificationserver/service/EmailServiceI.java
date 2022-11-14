package com.ss.parlour.notificationserver.service;

import com.ss.parlour.notificationserver.bean.EmailRequestBean;

public interface EmailServiceI {

    void sendEmail(EmailRequestBean emailRequestBean);
}

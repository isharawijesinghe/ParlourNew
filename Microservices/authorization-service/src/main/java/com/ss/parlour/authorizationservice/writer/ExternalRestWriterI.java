package com.ss.parlour.authorizationservice.writer;

import com.ss.parlour.authorizationservice.util.bean.requests.EmailRequestBean;

public interface ExternalRestWriterI {

    void sendNotificationMail(EmailRequestBean emailRequestBean);
}

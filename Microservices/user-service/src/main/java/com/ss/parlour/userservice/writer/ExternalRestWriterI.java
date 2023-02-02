package com.ss.parlour.userservice.writer;

import com.ss.parlour.userservice.util.bean.requests.EmailRequestBean;

public interface ExternalRestWriterI {

    void sendNotificationMail(EmailRequestBean emailRequestBean);
}

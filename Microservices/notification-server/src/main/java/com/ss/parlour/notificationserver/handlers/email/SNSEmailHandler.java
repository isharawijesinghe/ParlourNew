package com.ss.parlour.notificationserver.handlers.email;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.springframework.stereotype.Component;

@Component
public class SNSEmailHandler  implements EmailServiceHandlerI {

    @Override
    public EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean) {
        return null;
    }
}

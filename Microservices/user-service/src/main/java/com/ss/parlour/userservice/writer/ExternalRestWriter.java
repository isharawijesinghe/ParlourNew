package com.ss.parlour.userservice.writer;

import com.ss.parlour.userservice.util.bean.requests.EmailRequestBean;
import com.ss.parlour.userservice.util.bean.response.EmailResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalRestWriter implements ExternalRestWriterI{

    @Autowired
    private RestTemplate restTemplate;

    //This will send email invoke method in notification server
    public void sendNotificationMail(EmailRequestBean emailRequestBean){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailRequestBean> requestEntity = new HttpEntity<>(emailRequestBean, requestHeaders);

        restTemplate.exchange("http://NOTIFICATION-SERVICE/notification/sendEmailRequest",
                              HttpMethod.POST,
                              requestEntity,
                              EmailResponseBean.class
        );
    }
}

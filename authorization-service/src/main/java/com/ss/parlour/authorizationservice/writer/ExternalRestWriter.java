package com.ss.parlour.authorizationservice.writer;

import com.ss.parlour.authorizationservice.util.bean.EmailRequestBean;
import com.ss.parlour.authorizationservice.util.bean.EmailResponseBean;
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

    public void sendNotificationMail(EmailRequestBean emailRequestBean){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailRequestBean> requestEntity = new HttpEntity<>(emailRequestBean, requestHeaders);

        restTemplate.exchange("http://127.0.0.1:9005/email/sendEmail",
                HttpMethod.POST,
                requestEntity,
                EmailResponseBean.class
        );
    }
}

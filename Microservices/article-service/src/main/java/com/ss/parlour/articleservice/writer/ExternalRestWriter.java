package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.response.AuthorDetailResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ExternalRestWriter implements ExternalRestWriterI{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<AuthorDetailResponseBean> findAuthorDetailsByLoginName(String loginName){
        AuthorDetailResponseBean authorDetailResponseBean = restTemplate.getForObject("http://USER-SERVICE/user/findAuthorDetailsByLoginName/" + loginName,
                AuthorDetailResponseBean.class);
        return Optional.ofNullable(authorDetailResponseBean);
    }
}

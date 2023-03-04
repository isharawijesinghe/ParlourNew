package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.response.AuthorDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ExternalRestWriter implements ExternalRestWriterI{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<AuthorDetailResponse> findAuthorDetailsByLoginName(String loginName){
        AuthorDetailResponse authorDetailResponse = restTemplate.getForObject("http://USER-SERVICE/user/findAuthorDetailsByLoginName/" + loginName,
                AuthorDetailResponse.class);
        return Optional.ofNullable(authorDetailResponse);
    }
}

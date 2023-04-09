package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class ExternalRestWriter implements ExternalRestWriterI{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> findUserInfoDetailsById(String userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map , headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://USER-SERVICE/user/findUserInfoByUser")
                .queryParam("userId", userId);

        ResponseEntity<?> authorDetailResponse = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                ArticleResponse.class);
        return authorDetailResponse;
    }
}

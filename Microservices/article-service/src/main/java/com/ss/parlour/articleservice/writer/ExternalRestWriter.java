package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class ExternalRestWriter implements ExternalRestWriterI{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<ResponseEntity<ArticleResponse>> findAuthorDetailsByLoginName(String userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map ,
                headers);
        ResponseEntity<ArticleResponse> authorDetailResponse = restTemplate.exchange("http://USER-SERVICE/user/findUserInfoByUser/" + userId,
                        HttpMethod.GET, entity, ArticleResponse.class);
        return Optional.ofNullable(authorDetailResponse);
    }
}

package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.TopicAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/topics",consumes= MediaType.APPLICATION_JSON_VALUE)
public class TopicREST {

    @Autowired
    private ArticleServiceI articleServiceI;

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addTopic(@RequestBody TopicAddRequest topicAddRequest){
        ArticleResponse articleResponse = articleServiceI.addTopic(topicAddRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findAllTopic", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findAllTopic(){
        ArticleResponse articleResponse = articleServiceI.findAllTopic();
        return ResponseEntity.ok().body(articleResponse);
    }

}

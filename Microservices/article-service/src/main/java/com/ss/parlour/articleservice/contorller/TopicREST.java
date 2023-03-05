package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/topics",consumes= MediaType.APPLICATION_JSON_VALUE)
public class TopicREST {

    @Autowired
    private ArticleServiceI articleServiceI;

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addTopic(@RequestBody TopicAddRequest topicAddRequest){
        TopicAddResponse topicAddResponse = articleServiceI.addTopic(topicAddRequest);
        return ResponseEntity.ok().body(topicAddResponse);
    }

    @RequestMapping(value = "/findAllTopic", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<Object> findAllTopic(){
        TopicResponse topicAddResponse = articleServiceI.findAllTopic();
        return ResponseEntity.ok().body(topicAddResponse);
    }

}

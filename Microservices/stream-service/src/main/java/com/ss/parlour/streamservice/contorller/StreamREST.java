package com.ss.parlour.streamservice.contorller;

import com.ss.parlour.streamservice.service.StreamServiceI;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/stream",consumes= MediaType.APPLICATION_JSON_VALUE)
public class StreamREST {

    @Autowired
    private StreamServiceI streamServiceI;

    @RequestMapping(value = "/createStream", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> createStream(@RequestBody StreamCreateRequest streamCreateRequest){
        StreamCreateResponse streamCreateResponse = streamServiceI.createStream(streamCreateRequest);
        return ResponseEntity.ok().body(streamCreateResponse);
    }

    @RequestMapping(value = "/deleteStream", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteStream(@RequestBody StreamDeleteRequest streamDeleteRequest){
        StreamDeleteResponse streamDeleteResponse =  streamServiceI.deleteStream(streamDeleteRequest);
        return ResponseEntity.ok().body(streamDeleteResponse);
    }

    @RequestMapping(value = "/addArticleToStream", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addArticleToStream(@RequestBody ArticleToStreamRequest articleToStreamRequest){
        ArticleStreamAddResponse articleStreamAddResponse =  streamServiceI.addArticleToStream(articleToStreamRequest);
        return ResponseEntity.ok().body(articleStreamAddResponse);
    }

    @RequestMapping(value = "/findStreamByUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findStreamByUser(@RequestBody StreamRequest streamRequest){
        UserMappedStreamResponse userMappedStreamResponse =  streamServiceI.findStreamByUser(streamRequest);
        return ResponseEntity.ok().body(userMappedStreamResponse);
    }

    @RequestMapping(value = "/findArticlesByStream", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findArticlesByStream(@RequestBody StreamMappedArticleRequest streamMappedArticleRequest){
        StreamMappedArticleResponse streamMappedArticleResponse =  streamServiceI.findArticlesByStream(streamMappedArticleRequest);
        return ResponseEntity.ok().body(streamMappedArticleResponse);
    }
}

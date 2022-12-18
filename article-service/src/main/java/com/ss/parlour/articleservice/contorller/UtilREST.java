package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.UtilServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/util",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UtilREST {

    @Autowired
    private UtilServiceI utilServiceI;

    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(){
        String preSignUrl = utilServiceI.generatePreSignUrl();
        return ResponseEntity.ok().body(preSignUrl);
    }

//    @RequestMapping(value = "/createArticle", method = RequestMethod.POST, consumes = {"application/json"})
//    public ResponseEntity<Object> uploadImage(@RequestBody ArticleCreateRequestBean articleCreateRequestBean){
//        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.createArticle(articleCreateRequestBean);
//        return ResponseEntity.ok().body(articleCommonResponseBean);
//    }
}

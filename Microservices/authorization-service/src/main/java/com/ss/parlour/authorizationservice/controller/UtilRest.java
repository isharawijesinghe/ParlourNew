package com.ss.parlour.authorizationservice.controller;

import com.ss.parlour.authorizationservice.service.AuthServiceI;
import com.ss.parlour.authorizationservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.authorizationservice.util.bean.response.PreSignUrlResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/userutil",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UtilRest {

    @Autowired
    private AuthServiceI authServiceI;

    //AWS solution -> Generate S3 Bucket pre sign url and send link back to client side upload images
    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        PreSignUrlResponseBean preSignUrlResponseBean = authServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(preSignUrlResponseBean);
    }
}

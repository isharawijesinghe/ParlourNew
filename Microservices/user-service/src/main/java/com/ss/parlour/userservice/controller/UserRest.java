package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.AuthServiceI;
import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/user",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UserRest {

    @Autowired
    private UserServiceI userServiceI;

    //AWS solution -> Generate S3 Bucket pre sign url and send link back to client side upload images
    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        PreSignUrlResponseBean preSignUrlResponseBean = userServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(preSignUrlResponseBean);
    }
}

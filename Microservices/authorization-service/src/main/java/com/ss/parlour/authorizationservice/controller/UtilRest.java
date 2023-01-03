package com.ss.parlour.authorizationservice.controller;

import com.ss.parlour.authorizationservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/user/util",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UtilRest {

    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        return null;
    }
}

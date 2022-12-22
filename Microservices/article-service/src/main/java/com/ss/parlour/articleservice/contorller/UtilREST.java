package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.UtilServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/article/util",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UtilREST {

    @Autowired
    private UtilServiceI utilServiceI;

    @RequestMapping(value = "/upload/findFileByName", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findFileByName(@RequestBody PreSignUrlRequestBean preSignUrlRequestBean){
        return new ResponseEntity<>(utilServiceI.findFileByName(preSignUrlRequestBean), HttpStatus.OK);
    }


    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        return new ResponseEntity<>(utilServiceI.generatePreSignUrl(preSignUrlGenerateRequestBean), HttpStatus.OK);
    }


}

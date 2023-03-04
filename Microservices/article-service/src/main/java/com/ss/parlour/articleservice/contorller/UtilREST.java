package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.UtilServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequest preSignUrlGenerateRequest){
        PreSignUrlResponse preSignUrlResponse = utilServiceI.generatePreSignUrl(preSignUrlGenerateRequest);
        return ResponseEntity.ok(preSignUrlResponse);
    }


}

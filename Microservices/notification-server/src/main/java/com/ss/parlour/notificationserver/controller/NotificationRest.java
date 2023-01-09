package com.ss.parlour.notificationserver.controller;

import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.service.NotificationServiceI;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/notification",consumes= MediaType.APPLICATION_JSON_VALUE)
public class NotificationRest {

    @Autowired
    NotificationServiceI notificationServiceI;

    private static Logger logger= LogManager.getLogger("NotificationRest.class");

    @RequestMapping(value = "/sendEmailRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> sendEmailRequest(@RequestBody EmailRequestBean request){
        logger.debug("==== Send email request found: " + request);
        EmailResponseBean emailResponseBean = notificationServiceI.sendEmailRequest(request);
        return ResponseEntity.ok(emailResponseBean);
    }

}

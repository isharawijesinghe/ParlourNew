package com.ss.parlour.notificationserver.controller;

import com.ss.parlour.notificationserver.bean.MailRequestBean;
import com.ss.parlour.notificationserver.bean.MailResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email",consumes= MediaType.APPLICATION_JSON_VALUE)
public class EmailRest {

    private static Logger logger= LogManager.getLogger("EmailRest.class");

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST, consumes = {"application/json"})
    public MailResponseBean create(@RequestBody MailRequestBean request){
        logger.debug("==== Send email request found: " + request);
        return null;
    }

}

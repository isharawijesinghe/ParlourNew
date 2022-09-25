package com.ss.parlour.authenticationservice.controller;

import com.ss.parlour.authenticationservice.service.AuthServiceI;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/authentication",consumes=MediaType.APPLICATION_JSON_VALUE)
public class LoginREST {
    private static Logger logger= LogManager.getLogger("LoginREST.class");
    @Autowired
    private AuthServiceI authServiceI;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 "+System.currentTimeMillis();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"application/json"})
    public AuthResponseBean login(@RequestBody AuthRequestBean request){
        logger.debug("=Login request found:"+request);
        return authServiceI.login(request);
    }

    public AuthServiceI getAuthServiceI() {
        return authServiceI;
    }

    public void setAuthServiceI(AuthServiceI authServiceI) {
        this.authServiceI = authServiceI;
    }
}

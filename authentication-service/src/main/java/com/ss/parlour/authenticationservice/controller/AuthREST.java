package com.ss.parlour.authenticationservice.controller;

import com.ss.parlour.authenticationservice.service.AuthServiceI;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.AuthResponseBean;
import com.ss.parlour.authenticationservice.util.bean.UserRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserResponseBean;
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
public class AuthREST {
    private static Logger logger= LogManager.getLogger("LoginREST.class");
    @Autowired
    private AuthServiceI authServiceI;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"application/json"})
    public AuthResponseBean login(@RequestBody AuthRequestBean request){
        logger.debug("=Login request found:"+request);
        return authServiceI.login(request);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = {"application/json"})
    public UserResponseBean login(@RequestBody UserRequestBean userRequestBean){
        logger.debug("=Create user request found:"+userRequestBean);
        return authServiceI.createUser(userRequestBean);
    }

    @RequestMapping(value = "/changePW", method = RequestMethod.POST, consumes = {"application/json"})
    public UserResponseBean changePW(@RequestBody UserRequestBean userRequestBean){
        logger.debug("=Change password request found:"+userRequestBean);
        return authServiceI.changePW(userRequestBean);
    }

    public AuthServiceI getAuthServiceI() {
        return authServiceI;
    }

    public void setAuthServiceI(AuthServiceI authServiceI) {
        this.authServiceI = authServiceI;
    }
}

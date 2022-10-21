package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/authentication",consumes=MediaType.APPLICATION_JSON_VALUE)
public class UserREST {

    private static Logger logger= LogManager.getLogger("LoginREST.class");

    @Autowired
    private UserServiceI userServiceI;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = {"application/json"}, produces =  {"application/json"})
    public ResponseEntity<Object> createUser(@RequestBody UserRequestBean userRequestBean){
        logger.debug("=Create user request found:"+userRequestBean);
        UserResponseBean userResponseBean =  getUserServiceI().createUser(userRequestBean);
        return ResponseEntity.ok().body(userResponseBean);
    }

    @RequestMapping(value = "/changePW", method = RequestMethod.POST, consumes = {"application/json"})
    public UserResponseBean changePW(@RequestBody UserRequestBean userRequestBean){
        logger.debug("=Change password request found:"+userRequestBean);
        return getUserServiceI().changePW(userRequestBean);
    }

    public UserServiceI getUserServiceI() {
        return userServiceI;
    }

    public void setUserServiceI(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }
}

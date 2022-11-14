package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.UserCommonResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> createUser(@RequestBody UserRegisterRequestBean userRegisterRequestBean){
        logger.debug("=Create user request found:"+ userRegisterRequestBean);
        UserCommonResponseBean userCommonResponseBean =  getUserServiceI().registerUser(userRegisterRequestBean);
        return ResponseEntity.ok().body(userCommonResponseBean);
    }

    @RequestMapping(value = "/changePW", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> changePW(@RequestBody UserRegisterRequestBean userRegisterRequestBean){
        logger.debug("=Change password request found:"+ userRegisterRequestBean);
        UserCommonResponseBean userCommonResponseBean =  getUserServiceI().changePW(userRegisterRequestBean);
        return ResponseEntity.ok().body(userCommonResponseBean);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET, consumes = {"application/json"})
        public ResponseEntity<Object> confirm(@RequestParam("token") String token, @RequestParam("type") String type){
        UserCommonResponseBean userCommonResponseBean =  getUserServiceI().confirm(token, type);
        return ResponseEntity.ok().body(userCommonResponseBean);
    }

    public UserServiceI getUserServiceI() {
        return userServiceI;
    }

    public void setUserServiceI(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }
}

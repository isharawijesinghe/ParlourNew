package com.ss.parlour.authorizationservice.controller;

import com.ss.parlour.authorizationservice.dao.UserDAOI;
import com.ss.parlour.authorizationservice.repository.cassandra.UserRepositoryI;
import com.ss.parlour.authorizationservice.service.AuthServiceI;
import com.ss.parlour.authorizationservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.bean.response.UserRegistrationResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/authentication",consumes=MediaType.APPLICATION_JSON_VALUE)
public class AuthREST {

    private static Logger logger= LogManager.getLogger("LoginREST.class");

    @Autowired
    private AuthServiceI authServiceI;

    @Autowired
    private UserRepositoryI userRepository;

    @Autowired
    private UserDAOI userDAOI;


    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody AuthRequestBean authRequestBean){
        logger.debug("=Login request found: " + authRequestBean);
        AuthResponseBean authResponseBean = authServiceI.userLogin(authRequestBean);
        return ResponseEntity.ok(authResponseBean);
    }

    @RequestMapping(value = "/auth/createUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody UserRegisterRequestBean userRegisterRequestBean){
        logger.debug("=Create user request found: " + userRegisterRequestBean);
        UserRegistrationResponseBean userRegistrationResponseBean = authServiceI.registerUser(userRegisterRequestBean);
        return ResponseEntity.ok(userRegistrationResponseBean);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(result.getUsername()).toUri();

//        return ResponseEntity.created(location)
//                .body(new UserRegistrationResponseBean(true, "User registered successfully@"));
    }

}

package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.AuthServiceI;
import com.ss.parlour.userservice.util.bean.requests.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.response.TokenConfirmResponseBean;
import com.ss.parlour.userservice.util.bean.response.AuthResponseBean;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.bean.response.UserRegistrationResponseBean;
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

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/auth/signIn", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> signIn(@RequestBody AuthRequestBean authRequestBean){
        AuthResponseBean authResponseBean = authServiceI.signIn(authRequestBean);
        return ResponseEntity.ok(authResponseBean);
    }

    @RequestMapping(value = "/auth/signUp", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> signUp(@RequestBody UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = authServiceI.signUp(userRegisterRequestBean);
        return ResponseEntity.ok(userRegistrationResponseBean);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(result.getUsername()).toUri();

//        return ResponseEntity.created(location)
//                .body(new UserRegistrationResponseBean(true, "User registered successfully@"));
    }

    @RequestMapping(value = "/auth/signUpWithEmail", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> signUpWithEmail(@RequestBody UserRegisterRequestBean userRegisterRequestBean){
        UserRegistrationResponseBean userRegistrationResponseBean = authServiceI.signUpWithEmail(userRegisterRequestBean);
        return ResponseEntity.ok(userRegistrationResponseBean);
    }

    @RequestMapping(value = "/auth/tokenConfirm", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> tokenConfirm(@RequestBody TokenConfirmRequest tokenConfirmRequest){
        TokenConfirmResponseBean authCommonResponseBean = authServiceI.tokenConfirm(tokenConfirmRequest);
        return ResponseEntity.ok(authCommonResponseBean);
    }

}

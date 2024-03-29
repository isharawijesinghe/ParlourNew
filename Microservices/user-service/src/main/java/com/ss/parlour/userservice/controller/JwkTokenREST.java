package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.configurations.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
public class JwkTokenREST {

    @Autowired
    TokenProvider tokenProvider;

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getKey() {
        return tokenProvider.getPublicKeys();
    }

}

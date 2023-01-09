package com.ss.parlour.authorizationservice.util.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {

    public String generateLogicSecret(){
        return "" + UUID.randomUUID();
    }
}

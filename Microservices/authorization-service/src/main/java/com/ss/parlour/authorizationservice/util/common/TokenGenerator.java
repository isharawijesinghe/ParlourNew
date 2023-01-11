package com.ss.parlour.authorizationservice.util.common;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class TokenGenerator {

    public String generateLogicSecret(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public String generateUserTokenKey(String userName, String tokenType){
        return userName + "_" + tokenType;
    }
}

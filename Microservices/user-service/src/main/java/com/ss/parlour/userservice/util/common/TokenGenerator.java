package com.ss.parlour.userservice.util.common;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TokenGenerator {

    public String generateLogicSecret(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public String generateUniqueUserId(){return String.valueOf(UUIDs.timeBased());}

    public String generateUserTokenKey(String userName, String tokenType){
        return userName + "_" + tokenType;
    }
}

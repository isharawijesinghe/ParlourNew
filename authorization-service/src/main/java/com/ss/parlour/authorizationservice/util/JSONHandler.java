package com.ss.parlour.authorizationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.parlour.authorizationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authorizationservice.util.bean.UserRequestBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JSONHandler {
    private ObjectMapper mapper = new ObjectMapper();
    private static Logger logger= LogManager.getLogger("JSONHandler.class");

    public String getJSonString(Object pojo) {
        try {
            return mapper.writeValueAsString(pojo);

        } catch (JsonProcessingException e) {
           logger.error(e.getMessage(),e);
        }
        return null;
    }

    public <T> T parserJSON(String jsonMsg, Class<T> clz) {
        try {
            return mapper.readValue(jsonMsg, clz);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}

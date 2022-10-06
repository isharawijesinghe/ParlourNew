package com.ss.parlour.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHandler {
    private ObjectMapper mapper = new ObjectMapper();


    public String getJSonString(Object pojo) {
        try {
            return mapper.writeValueAsString(pojo);

        } catch (JsonProcessingException e) {
            throw new OMSCoreRuntimeException(e.getMessage(), e);
        }
    }

    public <T> T parserJSON(String jsonMsg, Class<T> clz) {
        try {
            return mapper.readValue(jsonMsg, clz);
        } catch (Exception e) {
            logger.error("LN:72", "JSON Request Passing Error:" + e.getMessage(), e);
            throw new OMSCoreRuntimeException(e.getMessage(), e);
        }
    }
}

package com.ss.parlour.articleservice.utils.bean.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import org.springframework.stereotype.Component;

@Component
public class ObjectHelper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, ArticleConst.STATUS_FALSE);
        return objectMapper;
    }
}

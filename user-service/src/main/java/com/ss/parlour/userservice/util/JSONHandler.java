package com.ss.parlour.userservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.parlour.userservice.util.bean.AuthRequestBean;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
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

    public static void main(String[] args) {
        UserRequestBean userRequestBean=new UserRequestBean();
        userRequestBean.setAddress("61/8c,test address");
        userRequestBean.setEmailDefault("samurdiwitharana123@gmail.com");
        userRequestBean.setFirstName("samurdi");
        userRequestBean.setLoginName("samurdi");
//        userRequestBean.setOldPW("123");
        userRequestBean.setPw("123");
        userRequestBean.setMobileNo("0773743222");

        AuthRequestBean authRequestBean=new AuthRequestBean();
        authRequestBean.setLoginName("samurdi");
        authRequestBean.setPw("123");
        JSONHandler jsonHandler=new JSONHandler();
        String json=jsonHandler.getJSonString(userRequestBean);
        System.out.println("==Json:"+json);
    }
}

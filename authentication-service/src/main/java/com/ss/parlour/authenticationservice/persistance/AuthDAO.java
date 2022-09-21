package com.ss.parlour.authenticationservice.persistance;

import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserBean;
import com.ss.parlour.authenticationservice.util.exception.runtime.ParlourGeneralException;
import org.springframework.stereotype.Component;

@Component
public class AuthDAO implements DAOI {

    public UserBean login(AuthRequestBean authRequestBean){
        //todo validate and return userBean
        UserBean userBean=null;
        if(userBean==null)
        throw new ParlourGeneralException("Invalid userName/password");
        return null;
    }
}

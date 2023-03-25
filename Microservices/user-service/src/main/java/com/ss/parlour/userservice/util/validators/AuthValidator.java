package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginEmailMapper;
import com.ss.parlour.userservice.util.bean.UserErrorCodes;
import com.ss.parlour.userservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.exception.UserRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator implements AuthValidatorI {

    @Autowired
    UserDAOI userDAOI;

    @Override
    public void validateSignUpRequest(UserRegisterRequestBean userRegisterRequestBean) {
        validateUserLoginNameExists(userRegisterRequestBean);
        validateUserEmailExists(userRegisterRequestBean);
    }

    @Override
    public void validateSignUpWithEmail(UserRegisterRequestBean userRegisterRequestBean){

    }

    @Override
    public void validateTokenConfirm(TokenConfirmRequest tokenConfirmRequest){

    }



    protected void validateUserLoginNameExists(UserRegisterRequestBean userRegisterRequestBean){
        UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean = userRegisterRequestBean.getUserRegisterRequestInnerBean();
        User existingUser = userDAOI.loadUserByLoginName(userRegisterRequestInnerBean.getLoginName());
        if (existingUser != null){
            throw new UserRuntimeException(UserErrorCodes.LOGIN_NAME_EXISTS);
        }
    }

    protected void validateUserEmailExists(UserRegisterRequestBean userRegisterRequestBean){
        UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean = userRegisterRequestBean.getUserRegisterRequestInnerBean();
        UserLoginEmailMapper existingUserLoginNameEmailMapper = userDAOI.loadLoginEmailMapperBean(userRegisterRequestInnerBean.getEmail());
        if (existingUserLoginNameEmailMapper != null){
            throw new UserRuntimeException(UserErrorCodes.EMAIL_EXISTS);
        }
    }
}

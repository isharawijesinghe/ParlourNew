package com.ss.parlour.authorizationservice.util.validators;

import com.ss.parlour.authorizationservice.dao.cassandra.UserDAOI;
import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.authorizationservice.util.bean.AuthorizationErrorCodes;
import com.ss.parlour.authorizationservice.util.bean.requests.TokenConfirmRequest;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.exception.AuthorizationRuntimeException;
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
        User existingUser = userDAOI.loadUserByLoginName(userRegisterRequestBean.getLoginName());
        if (existingUser != null){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.LOGIN_NAME_EXISTS);
        }
    }

    protected void validateUserEmailExists(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper existingUserLoginNameEmailMapper
                = userDAOI.loadLoginNameEmailMapperBean(userRegisterRequestBean.getEmail());
        if (existingUserLoginNameEmailMapper != null){
            throw new AuthorizationRuntimeException(AuthorizationErrorCodes.EMAIL_EXISTS);
        }
    }
}

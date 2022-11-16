package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.dao.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.util.bean.UserErrorCodes;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.exception.UserServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements UserValidatorI{

    @Autowired
    UserDAOI userDAOI;

    @Override
    public void validateCreateUserRequest(UserRegisterRequestBean userRegisterRequestBean) {
        validateUserLoginNameExists(userRegisterRequestBean);
        validateUserEmailExists(userRegisterRequestBean);
    }

    @Override
    public void validateChangePwRequest(UserRegisterRequestBean userRegisterRequestBean) {

    }

    protected void validateUserLoginNameExists(UserRegisterRequestBean userRegisterRequestBean){
        User existingUser = userDAOI.getUserByLoginName(userRegisterRequestBean.getLoginName());
        if (existingUser != null){
            throw new UserServiceRuntimeException(UserErrorCodes.LOGIN_NAME_EXISTS);
        }
    }

    protected void validateUserEmailExists(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper existingUserLoginNameEmailMapper
                = userDAOI.getLoginNameEmailMapperBean(userRegisterRequestBean.getEmail());
        if (existingUserLoginNameEmailMapper != null){
            throw new UserServiceRuntimeException(UserErrorCodes.EMAIL_EXISTS);
        }
    }
}

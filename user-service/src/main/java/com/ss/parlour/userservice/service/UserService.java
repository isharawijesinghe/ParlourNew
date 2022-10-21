package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.flow.UserFlowHandlerI;
import com.ss.parlour.userservice.util.bean.UserRequestBean;
import com.ss.parlour.userservice.util.bean.UserResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserServiceI{

    @Autowired
    private UserFlowHandlerI userFlowHandlerI;

    @Override
    public UserResponseBean createUser(UserRequestBean userRequestBean) {
         return userFlowHandlerI.createUser(userRequestBean);
    }

    @Override
    public UserResponseBean changePW(UserRequestBean userRequestBean) {
        UserResponseBean userResponseBean = new UserResponseBean();
        try {
            userResponseBean = userFlowHandlerI.changePW(userRequestBean);
        }catch (Exception ex){

        }
        return userResponseBean;
    }
}

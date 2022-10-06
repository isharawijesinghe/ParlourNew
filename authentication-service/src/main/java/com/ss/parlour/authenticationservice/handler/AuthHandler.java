package com.ss.parlour.authenticationservice.handler;

import com.ss.parlour.authenticationservice.repository.UserRepositoryI;
import com.ss.parlour.authenticationservice.util.bean.AuthRequestBean;
import com.ss.parlour.authenticationservice.domain.User;
import com.ss.parlour.authenticationservice.util.bean.Constants;
import com.ss.parlour.authenticationservice.util.bean.UserRequestBean;
import com.ss.parlour.authenticationservice.util.bean.UserResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements AuthHandlerI {

    @Autowired
    private UserRepositoryI userRepositoryI;

    public User login(AuthRequestBean authRequestBean){
        User user = userRepositoryI.findByLoginName(authRequestBean.getLoginName());
        if(authRequestBean.getPw().toUpperCase().trim().equals(user.getLoginPw())){
            return user;
        }
        return null;
    }

    public User createUser(User user){
        userRepositoryI.insert(user);
        return user;
    }

    public UserResponseBean createUser(UserRequestBean userRequestBean){
        User user=new User();
        user.setFirstName(userRequestBean.getFirstName());
        user.setAddress(userRequestBean.getAddress());
        user.setLoginName(userRequestBean.getLoginName());
        user.setLoginPw(System.currentTimeMillis()+userRequestBean.getLoginName()+System.currentTimeMillis()%4); //temp password
//        user.setCreatedDate(Calendar.getInstance().getTime().getTime()); //todo set other detail
        user = userRepositoryI.insert(user);
        UserResponseBean userResponseBean=new UserResponseBean();
        userResponseBean.setStatus(Constants.STATUS_SUCCESS);
        userResponseBean.setNarration("User created successfully with ID:");
        return userResponseBean;
    }

    @Override
    public UserResponseBean changePW(UserRequestBean userRequestBean) {
        UserResponseBean userResponseBean=new UserResponseBean();
        User user = userRepositoryI.findByLoginName(userRequestBean.getLoginName());
        if(user!=null && userRequestBean.getPw()!=null && userRequestBean.getPw().length()>0){
            if(userRequestBean.getOldPW()!=null && userRequestBean.getOldPW().toUpperCase().trim().equals(user.getLoginPw().toUpperCase())) {
                user.setLoginPw(userRequestBean.getPw());
                userRepositoryI.save(user);
                userResponseBean.setStatus(Constants.STATUS_SUCCESS);
                userResponseBean.setNarration("Password has changed successfully");
                return userResponseBean;
            }
        }
        userResponseBean.setNarration("Invalid login name or password");
        userResponseBean.setStatus(Constants.STATUS_FAILED);

        return userResponseBean;
    }


}

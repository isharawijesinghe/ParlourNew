package com.ss.parlour.userservice.dao;

import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;

public interface UserDAOI {

    User getUser(String userIdentification);
    User getUserByLoginName(String loginName);
    UserLoginNameEmailMapper getLoginNameEmailMapperBean(String email);
    void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean);
    User getUserByTokenAndType(String token, String type);
}

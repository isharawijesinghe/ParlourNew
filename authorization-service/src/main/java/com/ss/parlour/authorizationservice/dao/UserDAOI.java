package com.ss.parlour.authorizationservice.dao;

import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.authorizationservice.util.bean.UserRegisterRequestBean;

public interface UserDAOI {

    User loadUserByIdentification(String userIdentification);
    User saveUser(User user);
    User loadUserByIdentificationEmail(String email);
    UserLoginNameEmailMapper loadLoginNameEmailMapperBean(String email);
    User loadUserByLoginName(String loginName);
    void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean);
    User getUserByTokenAndType(String token, String type);
}

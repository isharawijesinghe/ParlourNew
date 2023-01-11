package com.ss.parlour.authorizationservice.dao.cassandra;

import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.authorizationservice.domain.cassandra.UserToken;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;

import java.util.Optional;

public interface UserDAOI {

    User loadUserByIdentification(String userIdentification);
    User saveUser(User user);
    User loadUserByIdentificationEmail(String email);
    UserLoginNameEmailMapper loadLoginNameEmailMapperBean(String email);
    User loadUserByLoginName(String loginName);
    void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean);
    User getUserByUserToken(String token, String type);
    void saveUserToken(UserRegisterRequestBean userRegisterRequestBean, String actionType);
    Optional<UserToken> getUserToken(String userName, String actionType);
}

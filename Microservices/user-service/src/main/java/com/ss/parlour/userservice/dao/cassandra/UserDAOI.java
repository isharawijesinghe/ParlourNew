package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.*;
import com.ss.parlour.userservice.util.bean.UserSignupHelperBean;

import java.util.List;
import java.util.Optional;

public interface UserDAOI {

    Optional<User> findUserByLoginName(String userIdentification);
    Optional<UserLoginNameEmailMapper> findUserByEmail(String userIdentification);
    User saveUser(User user);
    UserLoginNameEmailMapper loadLoginNameEmailMapperBean(String email);
    User loadUserByLoginName(String loginName);
    User getUserByUserToken(String token, String type);
    void saveUserToken(UserToken userToken);
    Optional<UserToken> getUserToken(String userName, String actionType);
    void saveUserInfo(UserInfo userInfo);
    Optional<UserInfo> getUserInfoFromDb(String loginName);
    void saveUserSignUpDataBeans(UserSignupHelperBean userSignupHelperBean);
    void saveUserInterests(List<Topics> topics);
    Optional<UserInterests> getUserInterestsByLoginName(String loginName);
}

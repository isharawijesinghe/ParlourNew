package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.*;
import com.ss.parlour.userservice.util.bean.UserInterestsAddHelperBean;
import com.ss.parlour.userservice.util.bean.UserSignupHelperBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;

import java.util.List;
import java.util.Optional;

public interface UserDAOI {

    Optional<User> findUserByUserId(String userId);
    Optional<UserLoginNameMapper> findUserByLoginName(String userIdentification);
    Optional<UserLoginEmailMapper> findUserByEmail(String userIdentification);
    User saveUser(User user);
    UserLoginEmailMapper loadLoginEmailMapperBean(String email);
    User loadUserByLoginName(String loginName);
    void saveUserToken(UserToken userToken);
    Optional<UserToken> getUserToken(String userName, String actionType);
    void saveUserInfo(UserInfo userInfo);
    Optional<UserInfo> getUserInfoFromDb(String loginName);
    void saveUserSignUpDataBeans(UserSignupHelperBean userSignupHelperBean);
    void saveUserInterests(UserInterestsAddHelperBean userInterestsAddHelperBean);
    Optional<List<UserInterestsByUser>> getUserInterestsByLoginName(String userId);
}

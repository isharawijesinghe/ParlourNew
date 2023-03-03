package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.util.bean.UserSignupHelperBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;

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
}

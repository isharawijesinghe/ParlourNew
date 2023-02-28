package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;

import java.util.Optional;

public interface UserDAOI {

    User loadUserByIdentification(String userIdentification);
    User saveUser(User user);
    User loadUserByIdentificationEmail(String email);
    UserLoginNameEmailMapper loadLoginNameEmailMapperBean(String email);
    User loadUserByLoginName(String loginName);
    void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean);
    User getUserByUserToken(String token, String type);
    void saveUserToken(UserRegisterRequestBean userRegisterRequestBean);
    Optional<UserToken> getUserToken(String userName, String actionType);
    void saveUserInfo(UserInfo userInfo);
    Optional<UserInfo> getUserInfoFromDb(UserInfoRequestBean userInfoRequestBean);
}

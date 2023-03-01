package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.repository.cassandra.LoginNameEmailMapperRepositoryI;
import com.ss.parlour.userservice.repository.cassandra.UserInfoRepositoryI;
import com.ss.parlour.userservice.repository.cassandra.UserRepositoryI;
import com.ss.parlour.userservice.repository.cassandra.UserTokenRepositoryI;
import com.ss.parlour.userservice.util.bean.AuthProvider;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.UserSignupHelperBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.common.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDAO implements UserDAOI{

    @Autowired
    private UserRepositoryI userRepositoryI;

    @Autowired
    private LoginNameEmailMapperRepositoryI loginNameEmailMapperRepositoryI;

    @Autowired
    private UserTokenRepositoryI userTokenRepositoryI;

    @Autowired
    private UserInfoRepositoryI userInfoRepositoryI;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public Optional<User> findUserByLoginName(String userIdentification){
        return userRepositoryI.findByLoginName(userIdentification);
    }

    @Override
    public Optional<UserLoginNameEmailMapper> findUserByEmail(String userIdentification){
        return loginNameEmailMapperRepositoryI.findByEmail(userIdentification);
    }

    @Override
    public User loadUserByLoginName(String loginName){
        Optional<User> existingUser = userRepositoryI.findByLoginName(loginName);
        if (existingUser.isPresent()){
            return existingUser.get();
        }
        return null;
    }

    @Override
    public User saveUser(User user){
        return userRepositoryI.save(user);
    }

    @Override
    public User getUserByUserToken(String userName, String actionType){
        Optional<UserToken> userTokenFromDb = userTokenRepositoryI.findByUserNameAndActionType(userName, actionType);
        if (userTokenFromDb.isPresent()) {
            String existingUserLoginName = userTokenFromDb.get().getUserName();
            Optional<User> existingUserFromDb = userRepositoryI.findByLoginName(existingUserLoginName);
            if (existingUserFromDb.isPresent()) {
                return existingUserFromDb.get();
            }
            return null;
        }
        return null;
    }

    @Override
    public UserLoginNameEmailMapper loadLoginNameEmailMapperBean(String email){
        Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb = loginNameEmailMapperRepositoryI.findByEmail(email);
        if (loginNameEmailMapperFromDb.isPresent()){
            return loginNameEmailMapperFromDb.get();
        }
        return null;
    }

    @Override
    public Optional<UserToken> getUserToken(String userName, String actionType){
        return userTokenRepositoryI.findByUserNameAndActionType(userName, actionType);
    }

    @Override
    public void saveUserToken(UserToken userToken){
        userTokenRepositoryI.insert(userToken);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo){
        userInfoRepositoryI.save(userInfo);
    }

    @Override
    public Optional<UserInfo> getUserInfoFromDb(UserInfoRequestBean userInfoRequestBean){
        return userInfoRepositoryI.findUserInfoByLoginName(userInfoRequestBean.getLoginName());
    }

    @Override
    public void saveUserSignUpDataBeans(UserSignupHelperBean userSignupHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertUserSignUpDataBeansInBatch(userSignupHelperBean, batchOps);
    }

    protected void insertUserSignUpDataBeansInBatch(UserSignupHelperBean userSignupHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(userSignupHelperBean.getUser());
        batchOps.insert(userSignupHelperBean.getLoginNameEmailMapper());
    }


}

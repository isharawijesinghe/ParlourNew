package com.ss.parlour.userservice.dao.cassandra;

import com.ss.parlour.userservice.domain.cassandra.*;
import com.ss.parlour.userservice.repository.cassandra.*;
import com.ss.parlour.userservice.util.bean.UserInterestsAddHelperBean;
import com.ss.parlour.userservice.util.bean.UserSignupHelperBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.common.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserDAO implements UserDAOI{

    @Autowired
    private UserRepositoryI userRepositoryI;

    @Autowired
    private UserLoginNameMapperRepositoryI userLoginNameMapperRepositoryI;

    @Autowired
    private UserLoginEmailMapperRepositoryI userLoginEmailMapperRepositoryI;

    @Autowired
    private UserTokenRepositoryI userTokenRepositoryI;

    @Autowired
    private UserInfoRepositoryI userInfoRepositoryI;

    @Autowired
    private UserInterestsRepositoryI userInterestsRepositoryI;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public Optional<User> findUserByUserId(String userId){
        return userRepositoryI.findByUserId(userId);
    }

    @Override
    public Optional<UserLoginNameMapper> findUserByLoginName(String userIdentification){
        return userLoginNameMapperRepositoryI.findByLoginName(userIdentification);
    }

    @Override
    public Optional<UserLoginEmailMapper> findUserByEmail(String userIdentification){
        return userLoginEmailMapperRepositoryI.findByEmail(userIdentification);
    }

    @Override
    public User loadUserByLoginName(String loginName){
        Optional<User> existingUser = userRepositoryI.findByUserId(loginName);
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
        Optional<UserToken> userTokenFromDb = userTokenRepositoryI.findByUserIdentificationAndActionType(userName, actionType);
        if (userTokenFromDb.isPresent()) {
            String existingUserLoginName = userTokenFromDb.get().getUserIdentification();
            Optional<User> existingUserFromDb = userRepositoryI.findByUserId(existingUserLoginName);
            if (existingUserFromDb.isPresent()) {
                return existingUserFromDb.get();
            }
            return null;
        }
        return null;
    }

    @Override
    public UserLoginEmailMapper loadLoginEmailMapperBean(String email){
        AtomicReference<UserLoginEmailMapper> currentUserLoginMapper = null;
        Optional<UserLoginEmailMapper> loginNameEmailMapperFromDb = userLoginEmailMapperRepositoryI.findByEmail(email);
        loginNameEmailMapperFromDb.ifPresent(loginNameEmailMapper -> {currentUserLoginMapper.set(loginNameEmailMapper);});
        return currentUserLoginMapper.get();
    }

    @Override
    public Optional<UserToken> getUserToken(String userName, String actionType){
        return userTokenRepositoryI.findByUserIdentificationAndActionType(userName, actionType);
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
    public Optional<UserInfo> getUserInfoFromDb(String userId){
        return userInfoRepositoryI.findUserInfoByUserId(userId);
    }

    @Override
    public void saveUserSignUpDataBeans(UserSignupHelperBean userSignupHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertUserSignUpDataBeansInBatch(userSignupHelperBean, batchOps);
    }

    @Override
    public void saveUserInterests(UserInterests userInterests){
        userInterestsRepositoryI.save(userInterests);
    }

    @Override
    public void saveUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        UserInterestsAddRequest.UserInterestsAddRequestBody userInterestsAddRequestBody = userInterestsAddRequest.getUserInterestsAddRequestBody();
        insertUserInterestsInBatch(userInterestsAddRequestBody.getTopicName(), userInterestsAddRequestBody.getUserId(), batchOps);
    }

    @Override
    public void saveUserInterests(UserInterestsAddHelperBean userInterestsAddHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertUserInterestsInBatch(userInterestsAddHelperBean, batchOps);
        batchOps.execute();
    }


    @Override
    public Optional<List<UserInterests>> getUserInterestsByLoginName(String userId){
        return userInterestsRepositoryI.findByUserId(userId);
    }

    protected void insertUserInterestsInBatch(List<String> topicName , String userId, CassandraBatchOperations batchOps){
        topicName.stream().forEach(topic -> batchOps.insert(new UserInterests(userId, topic, DateUtils.currentSqlTimestamp())));
        batchOps.execute();
    }

    protected void insertUserSignUpDataBeansInBatch(UserSignupHelperBean userSignupHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(userSignupHelperBean.getUser());
        batchOps.insert(userSignupHelperBean.getUserLoginEmailMapper());
        batchOps.insert(userSignupHelperBean.getUserLoginNameMapper());
        batchOps.execute();
    }

    protected void insertUserInterestsInBatch(UserInterestsAddHelperBean userInterestsAddHelperBean, CassandraBatchOperations batchOps){
        userInterestsAddHelperBean.getListOfUserInterests().forEach(
                interests -> {batchOps.insert(interests);}
        );

        userInterestsAddHelperBean.getListOfUserInterestsByUser().forEach(
                interestsByUser -> {batchOps.insert(interestsByUser);}
        );
    }


}

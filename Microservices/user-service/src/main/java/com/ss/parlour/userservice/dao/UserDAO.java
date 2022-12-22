package com.ss.parlour.userservice.dao;

import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserToken;
import com.ss.parlour.userservice.repository.cassandra.LoginNameEmailMapperRepositoryI;
import com.ss.parlour.userservice.repository.cassandra.UserRepositoryI;
import com.ss.parlour.userservice.repository.cassandra.UserTokenRepositoryI;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.UserRegisterRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDAO implements UserDAOI{

    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    LoginNameEmailMapperRepositoryI loginNameEmailMapperRepositoryI;

    @Autowired
    UserTokenRepositoryI userTokenRepositoryI;

    @Override
    public User getUserByLoginName(String loginName){
        Optional<User> existingUser = userRepositoryI.findByLoginName(loginName);
        if (existingUser.isPresent()){
            return existingUser.get();
        }
        return null;
    }

    @Override
    public UserLoginNameEmailMapper getLoginNameEmailMapperBean(String email){
        Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb =
                loginNameEmailMapperRepositoryI.findByEmail(email);
        if (loginNameEmailMapperFromDb.isPresent()){
            return loginNameEmailMapperFromDb.get();
        }
        return null;
    }

    @Override
    public User getUser(String userIdentification){
        User user = null;
        try {
            Optional<User> userFromDb= userRepositoryI.findByLoginName(userIdentification);
            if (userFromDb.isEmpty()){
                Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb
                        = loginNameEmailMapperRepositoryI.findByEmail(userIdentification);
                if (loginNameEmailMapperFromDb.isPresent()){
                    String loginName = loginNameEmailMapperFromDb.get().getLoginName();
                    userFromDb= userRepositoryI.findByLoginName(loginName);
                    if (userFromDb.isPresent()){
                        user = userFromDb.get();
                    }
                }
            }
        }catch (Exception ex){
            throw ex;
        }
        return user;
    }


    @Override
    public void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean){
        saveUser(userRegisterRequestBean);
        saveUserLoginNameEmailMapper(userRegisterRequestBean);
        saveUserToken(userRegisterRequestBean, UserConst.USER_ACTION_TYPE_REGISTER);
    }

    @Override
    public User getUserByTokenAndType(String token, String type){
        Optional<UserToken> userTokenFromDb =
                userTokenRepositoryI.findByTokenAndType(token, type);
        if (userTokenFromDb.isPresent()) {
            String existingUserLoginName = userTokenFromDb.get().getLoginName();
            Optional<User> existingUserFromDb = userRepositoryI.findByLoginName(existingUserLoginName);
            if (existingUserFromDb.isPresent()) {
                return existingUserFromDb.get();
            }
            return null;
        }
        return null;
    }

    private void saveUser(UserRegisterRequestBean userRegisterRequestBean){
        userRegisterRequestBean.setToken(UUID.randomUUID().toString());
        User user = populateUserForRegister(userRegisterRequestBean);
        userRepositoryI.insert(user);
    }

    private void saveUserLoginNameEmailMapper(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper =
                populateLoginNameEmailMapper(userRegisterRequestBean);
        loginNameEmailMapperRepositoryI.insert(loginNameEmailMapper);
    }

    private void saveUserToken(UserRegisterRequestBean userRegisterRequestBean, String actionType){
        UserToken userToken = populateUserToken(userRegisterRequestBean, actionType);
        userTokenRepositoryI.insert(userToken);
    }

    private User populateUserForRegister(UserRegisterRequestBean userRegisterRequestBean){
        User user = new User();
        user.setFirstName(userRegisterRequestBean.getFirstName());
        user.setLastName(userRegisterRequestBean.getLastName());
        user.setLoginName(userRegisterRequestBean.getLoginName());
        user.setEmail(userRegisterRequestBean.getEmail());
        user.setPassword(userRegisterRequestBean.getPassword());
        user.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setEnabled(UserConst.FALSE);
        user.setActiveToken(userRegisterRequestBean.getToken());
        return user;
    }

    private UserLoginNameEmailMapper populateLoginNameEmailMapper(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper = new UserLoginNameEmailMapper();
        loginNameEmailMapper.setEmail(userRegisterRequestBean.getEmail());
        loginNameEmailMapper.setLoginName(userRegisterRequestBean.getEmail());
        return loginNameEmailMapper;
    }

    private UserToken populateUserToken(UserRegisterRequestBean userRegisterRequestBean, String actionType){
        UserToken userToken = new UserToken();
        userToken.setLoginName(userRegisterRequestBean.getLoginName());
        userToken.setType(actionType);
        userToken.setToken(userRegisterRequestBean.getToken());
        return userToken;
    }


}

package com.ss.parlour.authorizationservice.dao.cassandra;

import com.ss.parlour.authorizationservice.domain.cassandra.User;
import com.ss.parlour.authorizationservice.domain.cassandra.UserLoginNameEmailMapper;
import com.ss.parlour.authorizationservice.domain.cassandra.UserToken;
import com.ss.parlour.authorizationservice.repository.cassandra.LoginNameEmailMapperRepositoryI;
import com.ss.parlour.authorizationservice.repository.cassandra.UserRepositoryI;
import com.ss.parlour.authorizationservice.repository.cassandra.UserTokenRepositoryI;
import com.ss.parlour.authorizationservice.util.bean.AuthProvider;
import com.ss.parlour.authorizationservice.util.bean.AuthorizationConst;
import com.ss.parlour.authorizationservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.authorizationservice.util.common.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TokenGenerator tokenGenerator;

    @Override
    public User loadUserByIdentification(String userIdentification){
        User user = null;
        try {
            Optional<User> userFromDb = userRepositoryI.findByLoginName(userIdentification);
            if (userFromDb.isEmpty()){
                Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb = loginNameEmailMapperRepositoryI.findByEmail(userIdentification);
                if (loginNameEmailMapperFromDb.isPresent()){
                    String loginName = loginNameEmailMapperFromDb.get().getLoginName();
                    userFromDb= userRepositoryI.findByLoginName(loginName);
                    if (userFromDb.isPresent()){
                        user = userFromDb.get();
                    }
                }
            } else {
                user = userFromDb.get();
            }
        }catch (Exception ex){
            throw ex;
        }
        return user;
    }

    @Override
    public User loadUserByIdentificationEmail(String email){
        User user = null;
        Optional<User> userFromDb = null;
        try {
            Optional<UserLoginNameEmailMapper> loginNameEmailMapperFromDb = loginNameEmailMapperRepositoryI.findByEmail(email);
            if (loginNameEmailMapperFromDb.isPresent()){
                String loginName = loginNameEmailMapperFromDb.get().getLoginName();
                userFromDb= userRepositoryI.findByLoginName(loginName);
                if (userFromDb.isPresent()){
                    user = userFromDb.get();
                } else {
                    new UsernameNotFoundException("User not found with email : " + email);
                }
            } else {
                new UsernameNotFoundException("User not found with email : " + email);
            }
            return user;
        }catch (Exception ex){
            throw ex;
        }
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
    public void saveUserDetails(UserRegisterRequestBean userRegisterRequestBean){
        saveUser(userRegisterRequestBean);
        saveUserLoginNameEmailMapper(userRegisterRequestBean);
       // saveUserToken(userRegisterRequestBean, AuthorizationConst.USER_ACTION_TYPE_REGISTER);
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
    public void saveUserToken(UserRegisterRequestBean userRegisterRequestBean){
        UserToken userToken = populateUserToken(userRegisterRequestBean.getEmail(), userRegisterRequestBean.getUserActionType());
        userTokenRepositoryI.insert(userToken);
    }

    private void saveUser(UserRegisterRequestBean userRegisterRequestBean){
        userRegisterRequestBean.setToken(UUID.randomUUID().toString());
        User user = populateUserForRegister(userRegisterRequestBean);
        userRepositoryI.insert(user);
    }

    private void saveUserLoginNameEmailMapper(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper = populateLoginNameEmailMapper(userRegisterRequestBean);
        loginNameEmailMapperRepositoryI.insert(loginNameEmailMapper);
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
        user.setEnabled(userRegisterRequestBean.getUserActionType().equals(AuthorizationConst.USER_ACTION_TYPE_PASSWORD_LESS_REGISTER) ?
                AuthorizationConst.TRUE : AuthorizationConst.FALSE);
        user.setActiveToken(userRegisterRequestBean.getToken());
        user.setProvider(AuthProvider.local);
        return user;
    }

    private UserLoginNameEmailMapper populateLoginNameEmailMapper(UserRegisterRequestBean userRegisterRequestBean){
        UserLoginNameEmailMapper loginNameEmailMapper = new UserLoginNameEmailMapper();
        loginNameEmailMapper.setEmail(userRegisterRequestBean.getEmail());
        loginNameEmailMapper.setLoginName(userRegisterRequestBean.getEmail());
        return loginNameEmailMapper;
    }

    private UserToken populateUserToken(String userName, String actionType){
        UserToken userToken = new UserToken();
        userToken.setUserName(userName);
        userToken.setActionType(actionType);
        userToken.setUserToken(tokenGenerator.generateLogicSecret());
        return userToken;
    }


}

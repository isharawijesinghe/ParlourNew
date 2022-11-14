package com.ss.parlour.authorizationservice.dao;

import com.ss.parlour.authorizationservice.domain.LoginNameEmailMapperRepositoryI;
import com.ss.parlour.authorizationservice.domain.User;
import com.ss.parlour.authorizationservice.repository.UserLoginNameEmailMapper;
import com.ss.parlour.authorizationservice.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO implements UserDAOI{

    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    LoginNameEmailMapperRepositoryI loginNameEmailMapperRepositoryI;

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
            } else {
                user = userFromDb.get();
            }
        }catch (Exception ex){
            throw ex;
        }
        return user;
    }

}

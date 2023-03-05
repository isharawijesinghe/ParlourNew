package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.userservice.handler.user.UserHandlerI;
import com.ss.parlour.userservice.util.bean.requests.*;
import com.ss.parlour.userservice.util.bean.response.*;
import com.ss.parlour.userservice.util.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService implements UserServiceI{

    @Autowired
    private UserValidatorI userValidatorI;

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Autowired
    private UserHandlerI userHandlerI;

    @Override
    public PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        userValidatorI.validatePreSignUrlRequest(preSignUrlGenerateRequestBean);
        CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
        return commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequestBean);
    }

    @Override
    public UserInfoUpdateResponseBean updateUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        return userHandlerI.updateUserInfo(userInfoUpdateRequestBean);
    }

    @Override
    public UserInfoResponseBean findUserInfoByUser(String loginName){
        return userHandlerI.findUserInfoByUser(loginName);
    }

    @Override
    public AuthorDetailResponseBean findAuthorDetailsById(String loginName){
        return userHandlerI.findAuthorDetailsById(loginName);
    }

    @Override
    public UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        return userHandlerI.addUserInterests(userInterestsAddRequest);
    }

    @Override
    public UserInterestsResponse findUserInterests(String loginName){
        return userHandlerI.findUserInterests(loginName);
    }
}

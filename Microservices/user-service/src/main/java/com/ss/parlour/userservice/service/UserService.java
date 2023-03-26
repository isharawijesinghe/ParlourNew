package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.userservice.handler.user.UserHandlerI;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.UserInterestsAddHelperBean;
import com.ss.parlour.userservice.util.bean.common.UserResponse;
import com.ss.parlour.userservice.util.bean.requests.*;
import com.ss.parlour.userservice.util.bean.response.*;
import com.ss.parlour.userservice.util.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class UserService implements UserServiceI{

    @Autowired
    private UserValidatorI userValidatorI;

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Autowired
    private UserHandlerI userHandlerI;

    @Override
    public UserResponse generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        userValidatorI.validatePreSignUrlRequest(preSignUrlGenerateRequestBean);
        CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
        PreSignUrlResponseBean preSignUrlResponseBean = commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequestBean);
        return  UserResponse.builder().body(preSignUrlResponseBean)
                .userMsgHeader(preSignUrlGenerateRequestBean.getUserMsgHeader())
                .message(UserConst.USER_IMAGE_UPLOAD_PRE_SIGN_SUCCESSFUL_NARRATION)
                .build();
    }

    @Override
    public UserResponse addUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = userHandlerI.addUserInfo(userInfoUpdateRequestBean);
        return  UserResponse.builder().body(userInfoUpdateResponseBean)
                .userMsgHeader(userInfoUpdateRequestBean.getUserMsgHeader())
                .message(UserConst.USER_INFO_UPDATE_SUCCESSFUL)
                .build();
    }

    @Override
    public UserResponse findUserInfoByUser(String loginName){
        UserInfoResponseBean userInfoResponseBean = userHandlerI.findUserInfoByUser(loginName);
        return  UserResponse.builder().body(userInfoResponseBean)
                .message(UserConst.USER_INFO_FOUND_SUCCESSFUL_NARRATION)
                .build();
    }

    @Override
    public UserResponse findAuthorDetailsById(String userId){
        AuthorDetailResponseBean authorDetailResponseBean = userHandlerI.findAuthorDetailsById(userId);
        return  UserResponse.builder().body(authorDetailResponseBean)
                .message(UserConst.USER_INFO_FOUND_SUCCESSFUL_NARRATION)
                .build();
    }

    @Override
    public UserResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        userHandlerI.addUserInterests(userInterestsAddRequest);
        return  UserResponse.builder()
                .message(UserConst.USER_INTERESTS_ADDED_SUCCESSFUL_NARRATION)
                .build();
    }

    @Override
    public UserResponse findUserInterests(String userId){
        UserInterestsResponse userInterestsResponse = userHandlerI.findUserInterests(userId);
        return  UserResponse.builder().body(userInterestsResponse)
                .message(UserConst.USER_INTERESTS_ADDED_SUCCESSFUL_NARRATION)
                .build();
    }
}

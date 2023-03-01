package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.userservice.handler.user.UserHandlerI;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoUpdateResponseBean;
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
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = userHandlerI.updateUserInfo(userInfoUpdateRequestBean);
        return userInfoUpdateResponseBean;
    }

    @Override
    public UserInfoResponseBean getUserInfo(UserInfoRequestBean userInfoRequestBean){
        UserInfoResponseBean userInfoResponseBean = userHandlerI.getUserInfo(userInfoRequestBean);
        return userInfoResponseBean;
    }
}

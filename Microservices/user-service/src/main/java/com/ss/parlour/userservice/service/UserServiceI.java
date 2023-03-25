package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.common.UserResponse;
import com.ss.parlour.userservice.util.bean.requests.*;
import com.ss.parlour.userservice.util.bean.response.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServiceI {

    UserResponse generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
    UserResponse addUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserResponse findUserInfoByUser(String loginName);
    UserResponse findAuthorDetailsById(String userId);
    UserResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest);
    UserResponse findUserInterests(String userId);
}

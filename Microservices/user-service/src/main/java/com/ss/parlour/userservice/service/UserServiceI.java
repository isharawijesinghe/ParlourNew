package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.requests.*;
import com.ss.parlour.userservice.util.bean.response.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServiceI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
    UserInfoUpdateResponseBean addUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserInfoResponseBean findUserInfoByUser(String loginName);
    AuthorDetailResponseBean findAuthorDetailsById(String loginName);
    UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest);
    UserInterestsResponse findUserInterests(String loginName);
}

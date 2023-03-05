package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.bean.response.*;

public interface UserHandlerI {

    UserInfoUpdateResponseBean addUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserInfoResponseBean findUserInfoByUser(String loginName);
    AuthorDetailResponseBean findAuthorDetailsById(String loginName);
    UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest);
    UserInterestsResponse findUserInterests(String loginName);
}

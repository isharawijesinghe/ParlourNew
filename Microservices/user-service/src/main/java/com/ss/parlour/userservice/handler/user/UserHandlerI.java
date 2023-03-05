package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsRequest;
import com.ss.parlour.userservice.util.bean.response.*;

public interface UserHandlerI {

    UserInfoUpdateResponseBean updateUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserInfoResponseBean findUserInfoByUser(UserInfoRequestBean userInfoRequestBean);
    AuthorDetailResponseBean findAuthorDetailsById(String loginName);
    UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest);
    UserInterestsResponse findUserInterests(UserInterestsRequest userInterestsRequest);
}

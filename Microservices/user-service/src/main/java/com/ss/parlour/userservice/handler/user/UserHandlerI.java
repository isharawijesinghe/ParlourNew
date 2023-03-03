package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthorDetailResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoUpdateResponseBean;

public interface UserHandlerI {

    UserInfoUpdateResponseBean updateUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserInfoResponseBean findUserInfoByUser(UserInfoRequestBean userInfoRequestBean);
    AuthorDetailResponseBean findAuthorDetailsById(String loginName);
}

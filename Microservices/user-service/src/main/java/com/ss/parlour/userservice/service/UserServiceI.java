package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthorDetailResponseBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoUpdateResponseBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServiceI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
    UserInfoUpdateResponseBean updateUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean);
    UserInfoResponseBean findUserInfoByUser(UserInfoRequestBean userInfoRequestBean);
    AuthorDetailResponseBean findAuthorDetailsById(String loginName);
}

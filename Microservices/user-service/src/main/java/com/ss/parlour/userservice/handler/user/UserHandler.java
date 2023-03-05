package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHandler implements UserHandlerI{

    @Autowired
    private UserDAOI userDAOI;

    @Override
    public UserInfoUpdateResponseBean addUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = new UserInfoUpdateResponseBean();
        UserInfo userInfo = populateUserInfoDataBean(userInfoUpdateRequestBean);
        userDAOI.saveUserInfo(userInfo);
        userInfoUpdateResponseBean.setStatus(UserConst.STATUS_SUCCESS);
        userInfoUpdateResponseBean.setNarration(UserConst.USER_INFO_UPDATE_SUCCESSFUL);
        return userInfoUpdateResponseBean;
    }

    @Override
    public UserInfoResponseBean findUserInfoByUser(String loginName){
        UserInfoResponseBean userInfoResponseBean = new UserInfoResponseBean();
        Optional<UserInfo> currentUserInfoFromDb=  userDAOI.getUserInfoFromDb(loginName);
        currentUserInfoFromDb.ifPresent(userInfo -> populateUserInfoResponseBean(userInfoResponseBean, userInfo));
        return userInfoResponseBean;
    }

    @Override
    public AuthorDetailResponseBean findAuthorDetailsById(String loginName){
        AuthorDetailResponseBean authorDetailResponseBean = new AuthorDetailResponseBean();
        Optional<UserInfo> currentUserInfoFromDb =  userDAOI.getUserInfoFromDb(loginName);
        currentUserInfoFromDb.ifPresent(userInfo -> {populateAuthorInfo(authorDetailResponseBean, userInfo);});
        return authorDetailResponseBean;
    }

    @Override
    public UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        UserInterestsAddResponse userInterestsAddResponse = new UserInterestsAddResponse();
        UserInterests userInterests = populateUserInterests(userInterestsAddRequest);
        userDAOI.saveUserInterests(userInterests);
        userInterestsAddResponse.setNarration(UserConst.USER_INTERESTS_ADDED_SUCCESSFUL_NARRATION);
        userInterestsAddResponse.setStatus(UserConst.STATUS_SUCCESS);
        return userInterestsAddResponse;
    }

    @Override
    public UserInterestsResponse findUserInterests(String loginName){
        UserInterestsResponse userInterestsResponse = new UserInterestsResponse();
        Optional<UserInterests> currentUserInterests = userDAOI.getUserInterestsByLoginName(loginName);
        currentUserInterests.ifPresent(userInterests -> userInterestsResponse.setTopicName(userInterests.getUserInterests()));
        return userInterestsResponse;
    }

    protected UserInterests populateUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        UserInterests userInterests = new UserInterests();
        userInterests.setLoginName(userInterestsAddRequest.getLoginName());
        userInterests.setUserInterests(userInterestsAddRequest.getTopicName());
        return userInterests;
    }

    protected UserInfo populateUserInfoDataBean(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(userInfoUpdateRequestBean.getLoginName());
        userInfo.setFirstName(userInfoUpdateRequestBean.getFirstName());
        userInfo.setLastName(userInfoUpdateRequestBean.getLastName());
        userInfo.setCountry(userInfoUpdateRequestBean.getCountry());
        userInfo.setJobTitle(userInfoUpdateRequestBean.getJobTitle());
        userInfo.setCompany(userInfoUpdateRequestBean.getCompany());
        userInfo.setExperience(userInfoUpdateRequestBean.getExperience());
        userInfo.setProfileImage(userInfoUpdateRequestBean.getProfileImage());
        userInfo.setDescription(userInfoUpdateRequestBean.getDescription());
        return userInfo;
    }

    protected void populateAuthorInfo(AuthorDetailResponseBean authorDetailResponseBean, UserInfo userInfo){
        authorDetailResponseBean.setName(userInfo.getFirstName());
        authorDetailResponseBean.setProfileImageUrl(userInfo.getProfileImage());
    }

    protected void populateUserInfoResponseBean(UserInfoResponseBean userInfoResponseBean, UserInfo userInfo){
        userInfoResponseBean.setLoginName(userInfo.getLoginName());
        userInfoResponseBean.setFirstName(userInfo.getFirstName());
        userInfoResponseBean.setLastName(userInfo.getLastName());
        userInfoResponseBean.setCountry(userInfo.getCompany());
        userInfoResponseBean.setJobTitle(userInfo.getJobTitle());
        userInfoResponseBean.setCompany(userInfo.getCompany());
        userInfoResponseBean.setExperience(userInfo.getExperience());
        userInfoResponseBean.setProfileImage(userInfo.getProfileImage());
        userInfoResponseBean.setDescription(userInfo.getDescription());
    }
}

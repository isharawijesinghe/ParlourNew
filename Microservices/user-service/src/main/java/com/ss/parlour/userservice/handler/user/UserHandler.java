package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import com.ss.parlour.userservice.domain.cassandra.UserInterestsByUser;
import com.ss.parlour.userservice.util.bean.UserInterestsAddHelperBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.bean.response.*;
import com.ss.parlour.userservice.util.common.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
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
        userInfoUpdateResponseBean.setUserId(userInfo.getUserId());
        return userInfoUpdateResponseBean;
    }

    @Override
    public UserInfoResponseBean findUserInfoByUser(String userId){
        UserInfoResponseBean userInfoResponseBean = new UserInfoResponseBean();
        Optional<UserInfo> currentUserInfoFromDb=  userDAOI.getUserInfoFromDb(userId);
        currentUserInfoFromDb.ifPresent(userInfo -> populateUserInfoResponseBean(userInfoResponseBean, userInfo));
        return userInfoResponseBean;
    }

    @Override
    public AuthorDetailResponseBean findAuthorDetailsById(String userId){
        AuthorDetailResponseBean authorDetailResponseBean = new AuthorDetailResponseBean();
        Optional<UserInfo> currentUserInfoFromDb =  userDAOI.getUserInfoFromDb(userId);
        currentUserInfoFromDb.ifPresent(userInfo -> {populateAuthorInfo(authorDetailResponseBean, userInfo);});
        return authorDetailResponseBean;
    }

    @Override
    public void addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        UserInterestsAddHelperBean userInterestsAddHelperBean = new UserInterestsAddHelperBean();
        userInterestsAddRequest.getUserInterestsAddRequestBody().setCreatedDate(DateUtils.currentSqlTimestamp());
        populateUserInterestsAddRequests(userInterestsAddHelperBean, userInterestsAddRequest);
        userDAOI.saveUserInterests(userInterestsAddHelperBean);
    }

    @Override
    public UserInterestsResponse findUserInterests(String userId){
        UserInterestsResponse userInterestsResponse = new UserInterestsResponse();
        Optional<List<UserInterests>> currentUserInterests = userDAOI.getUserInterestsByLoginName(userId);
        currentUserInterests.ifPresent(userInterests -> {
            userInterests.forEach(interests -> userInterestsResponse.getTopicName().add(interests.getTopic()));
        });
        return userInterestsResponse;
    }

    protected UserInfo populateUserInfoDataBean(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateRequestBean.UserInfoUpdateInnerRequestBean userInfoInner = userInfoUpdateRequestBean.getUserInfoUpdateInnerRequestBean();
        UserInfo userInfo = new UserInfo(userInfoInner);
        return userInfo;
    }

    protected void populateAuthorInfo(AuthorDetailResponseBean authorDetailResponseBean, UserInfo userInfo){
        authorDetailResponseBean.setName(userInfo.getFirstName());
        authorDetailResponseBean.setProfileImageUrl(userInfo.getProfileImage());
    }

    protected void populateUserInfoResponseBean(UserInfoResponseBean userInfoResponseBean, UserInfo userInfo){
        userInfoResponseBean.setUserId(userInfo.getUserId());
        userInfoResponseBean.setFirstName(userInfo.getFirstName());
        userInfoResponseBean.setLastName(userInfo.getLastName());
        userInfoResponseBean.setCountry(userInfo.getCompany());
        userInfoResponseBean.setJobTitle(userInfo.getJobTitle());
        userInfoResponseBean.setCompany(userInfo.getCompany());
        userInfoResponseBean.setExperience(userInfo.getExperience());
        userInfoResponseBean.setProfileImage(userInfo.getProfileImage());
        userInfoResponseBean.setDescription(userInfo.getDescription());
    }

    protected void populateUserInterestsAddRequests(UserInterestsAddHelperBean userInterestsAddHelperBean,
                                                    UserInterestsAddRequest userInterestsAddRequest){

        UserInterestsAddRequest.UserInterestsAddRequestBody userInterestsAddRequestBody = userInterestsAddRequest.getUserInterestsAddRequestBody();
        List<String> requestedTopics= userInterestsAddRequestBody.getTopicName();
        String userId = userInterestsAddRequestBody.getUserId();
        Timestamp createdDate = userInterestsAddRequestBody.getCreatedDate();
        requestedTopics.forEach(
                topicName -> {
                    userInterestsAddHelperBean.getListOfUserInterestsByUser().add(new UserInterestsByUser(userId, topicName, createdDate));
                    userInterestsAddHelperBean.getListOfUserInterests().add(new UserInterests(userId, topicName, createdDate));
                }
        );
    }
}

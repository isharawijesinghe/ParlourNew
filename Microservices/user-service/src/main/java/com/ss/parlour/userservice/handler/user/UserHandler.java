package com.ss.parlour.userservice.handler.user;

import com.ss.parlour.userservice.dao.cassandra.UserDAOI;
import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoUpdateResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHandler implements UserHandlerI{

    @Autowired
    private UserDAOI userDAOI;

    @Override
    public UserInfoUpdateResponseBean updateUserInfo(UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = new UserInfoUpdateResponseBean();
        UserInfo userInfo = populateUserInfoDataBean(userInfoUpdateRequestBean);
        userDAOI.saveUserInfo(userInfo);
        userInfoUpdateResponseBean.setStatus(UserConst.STATUS_SUCCESS);
        userInfoUpdateResponseBean.setNarration(UserConst.USER_INFO_UPDATE_SUCCESSFUL);
        return userInfoUpdateResponseBean;
    }

    @Override
    public UserInfoResponseBean getUserInfo(UserInfoRequestBean userInfoRequestBean){
        UserInfoResponseBean userInfoResponseBean = new UserInfoResponseBean();
        Optional<UserInfo> currentUserInfoFromDb=  userDAOI.getUserInfoFromDb(userInfoRequestBean);
        currentUserInfoFromDb.ifPresent(userInfo -> populateUserInfoResponseBean(userInfoResponseBean, userInfo));
        return userInfoResponseBean;
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

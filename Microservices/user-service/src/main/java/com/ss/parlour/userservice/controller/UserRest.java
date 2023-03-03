package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.response.AuthorDetailResponseBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoResponseBean;
import com.ss.parlour.userservice.util.bean.response.UserInfoUpdateResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/user",consumes= MediaType.APPLICATION_JSON_VALUE)
public class UserRest {

    @Autowired
    private UserServiceI userServiceI;

    //AWS solution -> Generate S3 Bucket pre sign url and send link back to client side upload images
    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        PreSignUrlResponseBean preSignUrlResponseBean = userServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(preSignUrlResponseBean);
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<UserInfoUpdateResponseBean> updateUserInfo(@RequestBody UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = userServiceI.updateUserInfo(userInfoUpdateRequestBean);
        return ResponseEntity.ok(userInfoUpdateResponseBean);
    }

    @RequestMapping(value = "/findUserInfoByUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<UserInfoResponseBean> findUserInfoByUser(@RequestBody UserInfoRequestBean userInfoRequestBean){
        UserInfoResponseBean userInfoResponseBean = userServiceI.findUserInfoByUser(userInfoRequestBean);
        return ResponseEntity.ok(userInfoResponseBean);
    }

    @RequestMapping(value = "/findAuthorDetailsByLoginName/{loginName}", method = RequestMethod.GET, consumes = {"application/json"})
    public AuthorDetailResponseBean findAuthorDetailsById(@PathVariable("loginName") String loginName){
        return userServiceI.findAuthorDetailsById(loginName);
    }
}

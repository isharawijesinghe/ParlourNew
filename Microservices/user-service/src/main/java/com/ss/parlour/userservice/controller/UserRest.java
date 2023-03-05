package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.requests.*;
import com.ss.parlour.userservice.util.bean.response.*;
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
    public ResponseEntity<?> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        PreSignUrlResponseBean preSignUrlResponseBean = userServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(preSignUrlResponseBean);
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = userServiceI.updateUserInfo(userInfoUpdateRequestBean);
        return ResponseEntity.ok(userInfoUpdateResponseBean);
    }

    @RequestMapping(value = "/findUserInfoByUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findUserInfoByUser(@RequestBody UserInfoRequestBean userInfoRequestBean){
        UserInfoResponseBean userInfoResponseBean = userServiceI.findUserInfoByUser(userInfoRequestBean);
        return ResponseEntity.ok(userInfoResponseBean);
    }

    @RequestMapping(value = "/findAuthorDetailsByLoginName/{loginName}", method = RequestMethod.GET, consumes = {"application/json"})
    public AuthorDetailResponseBean findAuthorDetailsById(@PathVariable("loginName") String loginName){
        return userServiceI.findAuthorDetailsById(loginName);
    }

    @RequestMapping(value = "/addUserInterests", method = RequestMethod.POST, consumes = {"application/json"})
    public UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        return userServiceI.addUserInterests(userInterestsAddRequest);
    }

    @RequestMapping(value = "/findUserInterests", method = RequestMethod.POST, consumes = {"application/json"})
    public UserInterestsResponse findUserInterests(UserInterestsRequest userInterestsRequest){
        return userServiceI.findUserInterests(userInterestsRequest);
    }

}

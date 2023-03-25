package com.ss.parlour.userservice.controller;

import com.ss.parlour.userservice.service.UserServiceI;
import com.ss.parlour.userservice.util.bean.common.UserResponse;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import com.ss.parlour.userservice.util.bean.requests.UserInterestsAddRequest;
import com.ss.parlour.userservice.util.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/user")
public class UserRest {

    @Autowired
    private UserServiceI userServiceI;

    /***
     * Generate Bucket pre sign url and send link back to client side upload images
     * @param generatePreSignUrl
     * @return PreSignUrlResponseBean
     */
    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        UserResponse userResponse = userServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(userResponse);
    }

    /***
     * Update user info details
     * Client send update info and backend will update data in db
     * @param userInfoUpdateRequestBean
     * @return UserInfoUpdateResponseBean
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addUserInfo(@RequestBody UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserResponse userResponse = userServiceI.addUserInfo(userInfoUpdateRequestBean);
        return ResponseEntity.ok(userResponse);
    }

    /***
     * Based on get request populate and send user info back to client
     * @param loginName
     * @return UserInfoResponseBean
     */
    @RequestMapping(value = "/findUserInfoByUser", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findUserInfoByUser(@RequestParam("loginName") String loginName){
        UserResponse userResponse = userServiceI.findUserInfoByUser(loginName);
        return ResponseEntity.ok(userResponse);
    }

    /***
     * Load author detail and send to internal service
     * @param userId
     * @return AuthorDetailResponseBean
     */
    @RequestMapping(value = "/findAuthorDetailsByLoginName", method = RequestMethod.GET, consumes = {"application/json"})
    public UserResponse findAuthorDetailsById(@RequestParam("userId") String userId){
        return userServiceI.findAuthorDetailsById(userId);
    }

    /***
     * Add user interests topics
     * @param userInterestsAddRequest
     * @return UserInterestsAddResponse
     */
    @RequestMapping(value = "/addUserInterests", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addUserInterests(@RequestBody UserInterestsAddRequest userInterestsAddRequest){
        UserResponse userResponse = userServiceI.addUserInterests(userInterestsAddRequest);
        return ResponseEntity.ok(userResponse);
    }

    /***
     * Load and populate user interests and send back to client
     * @param userId
     * @return UserInterestsResponse
     */
    @RequestMapping(value = "/findUserInterests", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findUserInterests(@RequestParam("userId") String userId){
        UserResponse userResponse = userServiceI.findUserInterests(userId);
        return ResponseEntity.ok(userResponse);
    }

}

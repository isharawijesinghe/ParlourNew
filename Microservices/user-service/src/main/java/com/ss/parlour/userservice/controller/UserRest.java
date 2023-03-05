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

    /***
     * Generate Bucket pre sign url and send link back to client side upload images
     * @param generatePreSignUrl
     * @return PreSignUrlResponseBean
     */
    @RequestMapping(value = "/upload/generatePreSignUrl", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> generatePreSignUrl(@RequestBody PreSignUrlGenerateRequestBean generatePreSignUrl){
        PreSignUrlResponseBean preSignUrlResponseBean = userServiceI.generatePreSignUrl(generatePreSignUrl);
        return ResponseEntity.ok(preSignUrlResponseBean);
    }

    /***
     * Update user info details
     * Client send update info and backend will update data in db
     * @param userInfoUpdateRequestBean
     * @return UserInfoUpdateResponseBean
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoUpdateRequestBean userInfoUpdateRequestBean){
        UserInfoUpdateResponseBean userInfoUpdateResponseBean = userServiceI.updateUserInfo(userInfoUpdateRequestBean);
        return ResponseEntity.ok(userInfoUpdateResponseBean);
    }

    /***
     * Based on get request populate and send user info back to client
     * @param loginName
     * @return UserInfoResponseBean
     */
    @RequestMapping(value = "/findUserInfoByUser/{loginName}", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findUserInfoByUser(@PathVariable("loginName") String loginName){
        UserInfoResponseBean userInfoResponseBean = userServiceI.findUserInfoByUser(loginName);
        return ResponseEntity.ok(userInfoResponseBean);
    }

    /***
     * Load author detail and send to internal service
     * @param loginName
     * @return AuthorDetailResponseBean
     */
    @RequestMapping(value = "/findAuthorDetailsByLoginName/{loginName}", method = RequestMethod.GET, consumes = {"application/json"})
    public AuthorDetailResponseBean findAuthorDetailsById(@PathVariable("loginName") String loginName){
        return userServiceI.findAuthorDetailsById(loginName);
    }

    /***
     * Add user interests topics
     * @param userInterestsAddRequest
     * @return UserInterestsAddResponse
     */
    @RequestMapping(value = "/addUserInterests", method = RequestMethod.POST, consumes = {"application/json"})
    public UserInterestsAddResponse addUserInterests(UserInterestsAddRequest userInterestsAddRequest){
        return userServiceI.addUserInterests(userInterestsAddRequest);
    }

    /***
     * Load and populate user interests and send back to client
     * @param loginName
     * @return UserInterestsResponse
     */
    @RequestMapping(value = "/findUserInterests/{loginName}", method = RequestMethod.GET, consumes = {"application/json"})
    public UserInterestsResponse findUserInterests(@PathVariable("loginName") String loginName){
        return userServiceI.findUserInterests(loginName);
    }

}

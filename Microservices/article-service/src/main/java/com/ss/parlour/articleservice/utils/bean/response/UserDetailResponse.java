package com.ss.parlour.articleservice.utils.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailResponse {

    private String name;
    private String profileImageUrl;

    public UserDetailResponse(UserInfoResponseBean userInfoResponseBean){
        this.name = userInfoResponseBean.getFirstName();
        this.profileImageUrl = userInfoResponseBean.getProfileImage();
    }
}

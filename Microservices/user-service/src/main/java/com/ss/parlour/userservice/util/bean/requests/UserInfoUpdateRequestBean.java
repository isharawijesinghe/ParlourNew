package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoUpdateRequestBean extends UserHeader {

    @JsonProperty("body")
    private UserInfoUpdateInnerRequestBean userInfoUpdateInnerRequestBean;

    @Getter
    @Setter
    public class UserInfoUpdateInnerRequestBean{
        private String loginName;
        private String firstName;
        private String lastName;
        private String country;
        private String jobTitle;
        private String company;
        private String experience;
        private String profileImage;
        private String description;
    }
}

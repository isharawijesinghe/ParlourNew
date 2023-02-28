package com.ss.parlour.userservice.util.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoUpdateRequestBean {

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

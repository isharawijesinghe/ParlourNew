package com.ss.parlour.userservice.util.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequestBean {

    private String loginName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;
    private String userActionType;

}

package com.ss.parlour.userservice.util.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestBean {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}

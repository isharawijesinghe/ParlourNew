package com.ss.parlour.userservice.util.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationResponseBean {
    private String actionType;
    private boolean isEmailVerificationRequired = false;
    private String email;

}

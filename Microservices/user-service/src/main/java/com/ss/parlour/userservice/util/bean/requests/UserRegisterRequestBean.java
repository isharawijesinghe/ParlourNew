package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequestBean extends UserHeader {

    @JsonProperty("body")
    private UserRegisterRequestInnerBean userRegisterRequestInnerBean;

    @Getter
    @Setter
    public class UserRegisterRequestInnerBean{
        private String userId;
        private String loginName;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String token;
        private String userActionType;
    }

}

package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestBean extends UserHeader {

    @JsonProperty("body")
    private AuthRequestInnerBean authRequestInnerBean;

    @Getter
    @Setter
    public class AuthRequestInnerBean {
        private String userIdentification;
        private String password;
    }

}

package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailRequestBean extends UserHeader {

    @JsonProperty("body")
    private EmailRequestInnerBean emailRequestInnerBean;

    @Getter
    @Setter
    public class EmailRequestInnerBean{
        private String receiverEmail;
        private String actionType;
        private String confirmationToken;
    }



}

package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenConfirmRequest extends UserHeader {

    @JsonProperty("body")
    private TokenConfirmInnerRequest tokenConfirmInnerRequest;

    @Getter
    @Setter
    public class TokenConfirmInnerRequest{
        private String userIdentification; //Either email / login name or any other identity
        private String token;
        private String actionType;
    }

}

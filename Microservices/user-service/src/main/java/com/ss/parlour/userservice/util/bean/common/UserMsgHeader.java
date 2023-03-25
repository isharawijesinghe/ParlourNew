package com.ss.parlour.userservice.util.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.UserConst;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMsgHeader {

    private String tenantCode = UserConst.DEFAULT_TENANCY_CODE;
    private String userId;
    private String clientIP;
}

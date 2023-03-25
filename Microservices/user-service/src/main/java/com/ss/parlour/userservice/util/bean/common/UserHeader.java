package com.ss.parlour.userservice.util.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class UserHeader {

    @JsonProperty("header")
    private UserMsgHeader userMsgHeader;
}

package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreSignUrlGenerateRequestBean extends UserHeader {

    @JsonProperty("body")
    private PreSignUrlGenerateInnerRequestBean preSignUrlGenerateInnerRequestBean;

    @Getter
    @Setter
    public class PreSignUrlGenerateInnerRequestBean{
        private String extension;
    }

}

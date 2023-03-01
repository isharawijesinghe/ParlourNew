package com.ss.parlour.userservice.util.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseBean {
    private int status;
    private String description;
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseBean(String accessToken) {
        this.setAccessToken(accessToken);
    }

}

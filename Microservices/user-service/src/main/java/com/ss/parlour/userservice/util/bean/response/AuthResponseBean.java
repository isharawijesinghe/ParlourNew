package com.ss.parlour.userservice.util.bean.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseBean {
    private boolean isAuthenticated;
    private String userId;
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseBean(String userId, String accessToken) {
        this.userId = userId;
        this.setAccessToken(accessToken);
    }

}

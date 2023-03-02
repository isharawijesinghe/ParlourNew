package com.ss.parlour.userservice.util.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationResponseBean {
    private boolean status;
    private String narration;
    private String actionType;

    public UserRegistrationResponseBean(boolean status, String narration) {
        this.setStatus(status);
        this.setNarration(narration);
    }

}

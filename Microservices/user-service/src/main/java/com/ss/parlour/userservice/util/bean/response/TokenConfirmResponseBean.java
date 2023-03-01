package com.ss.parlour.userservice.util.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenConfirmResponseBean {

    private int status;
    private String narration;
    private boolean isTokenConfirmSuccess = false;

    public TokenConfirmResponseBean(int status, String narration) {
        this.setStatus(status);
        this.setNarration(narration);
    }

}

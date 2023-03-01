package com.ss.parlour.userservice.util.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenConfirmRequest {

    private String userName;
    private String token;
    private String actionType;

}

package com.ss.parlour.userservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.Table;

@Table("usertoken")
public class UserToken {

    private String loginName;
    private String type;
    private String token;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
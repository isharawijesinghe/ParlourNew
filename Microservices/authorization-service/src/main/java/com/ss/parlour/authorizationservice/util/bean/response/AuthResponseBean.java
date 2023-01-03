package com.ss.parlour.authorizationservice.util.bean.response;

public class AuthResponseBean {
    private int status;
    private String description;
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseBean(String accessToken) {
        this.setAccessToken(accessToken);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

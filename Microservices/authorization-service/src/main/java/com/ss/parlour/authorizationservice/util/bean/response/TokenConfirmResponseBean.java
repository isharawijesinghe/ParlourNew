package com.ss.parlour.authorizationservice.util.bean.response;

public class TokenConfirmResponseBean {

    private int status;
    private String narration;
    private boolean isTokenConfirmSuccess = false;

    public TokenConfirmResponseBean(int status, String narration) {
        this.setStatus(status);
        this.setNarration(narration);
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public boolean isTokenConfirmSuccess() {
        return isTokenConfirmSuccess;
    }

    public void setTokenConfirmSuccess(boolean tokenConfirmSuccess) {
        isTokenConfirmSuccess = tokenConfirmSuccess;
    }
}

package com.ss.parlour.authorizationservice.util.bean.response;

public class UserRegistrationResponseBean {
    private boolean status;
    private String narration;

    public UserRegistrationResponseBean(){}

    public UserRegistrationResponseBean(boolean status, String narration) {
        this.setStatus(status);
        this.setNarration(narration);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}

package com.ss.parlour.authorizationservice.util.bean.response;

public class UserRegistrationResponseBean {
    private boolean status;
    private String narration;
    private String actionType;

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

    public String getActionType() {return actionType;}

    public void setActionType(String actionType) {this.actionType = actionType;}
}

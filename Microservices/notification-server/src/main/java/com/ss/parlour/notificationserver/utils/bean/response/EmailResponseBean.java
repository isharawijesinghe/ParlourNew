package com.ss.parlour.notificationserver.utils.bean.response;

public class EmailResponseBean {

    private int status;
    private String narration;

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

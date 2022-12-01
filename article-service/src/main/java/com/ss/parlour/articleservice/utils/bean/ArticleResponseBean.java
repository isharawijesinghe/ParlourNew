package com.ss.parlour.articleservice.utils.bean;

public class ArticleResponseBean {

    private int status;
    private String narration;

    public ArticleResponseBean(){}

    public ArticleResponseBean(int status, String narration){
        this.setStatus(status);
        this.setNarration(narration);
    }

    public int getStatus() {
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
}

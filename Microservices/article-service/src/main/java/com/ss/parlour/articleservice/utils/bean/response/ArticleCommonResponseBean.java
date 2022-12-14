package com.ss.parlour.articleservice.utils.bean.response;

public class ArticleCommonResponseBean {

    private String articleId;
    private int status;
    private String narration;

    public ArticleCommonResponseBean(){}

    public ArticleCommonResponseBean(int status, String narration){
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}

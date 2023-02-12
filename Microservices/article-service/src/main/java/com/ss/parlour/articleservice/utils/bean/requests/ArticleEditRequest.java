package com.ss.parlour.articleservice.utils.bean.requests;

public class ArticleEditRequest {

    private String articleId;
    private String requestUserId;
    private String originalAuthorId;
    private String requestMessage;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getOriginalAuthorId() {
        return originalAuthorId;
    }

    public void setOriginalAuthorId(String originalAuthorId) {
        this.originalAuthorId = originalAuthorId;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}

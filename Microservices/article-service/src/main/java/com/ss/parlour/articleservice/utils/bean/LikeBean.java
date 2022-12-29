package com.ss.parlour.articleservice.utils.bean;

public class LikeBean {

    private String articleId;
    private String commentId;
    private String userName;
    private int status;
    private LikeType likeType;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LikeType getLikeType() {return likeType;}

    public void setLikeType(LikeType likeType) {
        this.likeType = likeType;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}

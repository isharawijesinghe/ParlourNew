package com.ss.parlour.articleservice.utils.bean;

import java.sql.Timestamp;
import java.util.List;

public class CommentBean {
    private String id;
    private String articleId;
    private String parentId;
    private String authorName;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<CommentBean> subCommentBean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<CommentBean> getSubCommentBean() {return subCommentBean;}

    public void setSubCommentBean(List<CommentBean> subCommentBean) {this.subCommentBean = subCommentBean;}
}

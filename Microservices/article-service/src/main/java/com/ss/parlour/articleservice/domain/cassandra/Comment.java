package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Timestamp;
import java.util.*;

@Table("comment")
@UserDefinedType
public class Comment {

    @PrimaryKey
    private String id;
    private String articleId;
    private String parentId;
    private String userName;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Set<String> likedList = new HashSet<>();
    private Set<String> unLikedList = new HashSet<>();
    @org.springframework.data.annotation.Transient
    private List<Comment> subComments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<Comment> subComments) {
        this.subComments = subComments;
    }


    public Set<String> getLikedList() {
        return likedList;
    }

    public void setLikedList(Set<String> likedList) {
        this.likedList = likedList;
    }

    public Set<String> getUnLikedList() {
        return unLikedList;
    }

    public void setUnLikedList(Set<String> unLikedList) {
        this.unLikedList = unLikedList;
    }
}

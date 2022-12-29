package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.*;

@Table("article")
public class Article {

    @PrimaryKey
    private String id;
    private String userName;
    private String title;
    private String summary;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Set<String> likedList = new HashSet<>();
    private Set<String> unLikedList = new HashSet<>();

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getStatus() {return status;}

    public void setStatus(int status) {
        this.status = status;
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

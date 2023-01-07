package com.ss.parlour.streamservice.utils.bean;

import java.sql.Timestamp;

public class StreamBean {

    private String streamId;
    private String userName;
    private String description;
    private Timestamp createdDate;

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Timestamp getCreatedDate() {return createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}
}

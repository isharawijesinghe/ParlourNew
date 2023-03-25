package com.ss.parlour.streamservice.utils.bean;

import java.sql.Timestamp;

public class StreamBean {

    private String streamId;
    private String userId;
    private String description;
    private Timestamp createdDate;

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Timestamp getCreatedDate() {return createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}
}

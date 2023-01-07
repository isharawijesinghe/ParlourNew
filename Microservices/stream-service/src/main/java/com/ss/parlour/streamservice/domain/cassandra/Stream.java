package com.ss.parlour.streamservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("stream")
public class Stream {

    @PrimaryKey
    private String streamId;
   // @PrimaryKey
    private String userName;
    private String description;
    private Timestamp createdDate;

    public String getStreamId() {return streamId;}

    public void setStreamId(String streamId) {this.streamId = streamId;}

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public Timestamp getCreatedDate() {return createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}

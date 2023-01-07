package com.ss.parlour.streamservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("stream")
public class Stream {

    @PrimaryKey
    private String streamName;
   // @PrimaryKey
    private String userName;
    private String description;
    private Timestamp createdDate;

    public String getStreamName() {return streamName;}

    public void setStreamName(String streamName) {this.streamName = streamName;}

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public Timestamp getCreatedDate() {return createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}

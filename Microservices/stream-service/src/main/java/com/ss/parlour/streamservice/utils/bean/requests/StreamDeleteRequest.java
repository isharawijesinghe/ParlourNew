package com.ss.parlour.streamservice.utils.bean.requests;

public class StreamDeleteRequest {

    private String streamName;
    private String userName;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

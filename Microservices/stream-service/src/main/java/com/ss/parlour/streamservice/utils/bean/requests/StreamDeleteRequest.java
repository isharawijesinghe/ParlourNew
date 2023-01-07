package com.ss.parlour.streamservice.utils.bean.requests;

public class StreamDeleteRequest {

    private String streamId;
    private String userName;

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
}

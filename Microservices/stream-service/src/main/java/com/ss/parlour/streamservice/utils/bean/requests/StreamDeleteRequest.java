package com.ss.parlour.streamservice.utils.bean.requests;

public class StreamDeleteRequest {

    private String streamId;
    private String userId;

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
}

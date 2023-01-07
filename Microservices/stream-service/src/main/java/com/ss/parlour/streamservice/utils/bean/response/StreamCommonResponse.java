package com.ss.parlour.streamservice.utils.bean.response;

public class StreamCommonResponse {

    private String streamId;
    private int status;
    private String narration;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getStreamId() {return streamId;}

    public void setStreamId(String streamId) {this.streamId = streamId;}
}

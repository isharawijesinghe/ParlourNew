package com.ss.parlour.streamservice.utils.bean.response;

import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;

public class UserMappedStreamResponse {

    private UserMappedStream userMappedStream;

    public UserMappedStream getUserMappedStream() {
        return userMappedStream;
    }

    public void setUserMappedStream(UserMappedStream userMappedStream) {
        this.userMappedStream = userMappedStream;
    }
}

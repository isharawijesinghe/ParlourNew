package com.ss.parlour.streamservice.utils.bean.response;

import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;

public class StreamMappedArticleResponse {

    private StreamMappedArticles streamMappedArticles;

    public StreamMappedArticles getStreamMapArticles() {
        return streamMappedArticles;
    }

    public void setStreamMapArticles(StreamMappedArticles streamMappedArticles) {
        this.streamMappedArticles = streamMappedArticles;
    }
}

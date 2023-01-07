package com.ss.parlour.streamservice.utils.bean.response;

import com.ss.parlour.streamservice.domain.cassandra.StreamMapArticles;

public class StreamMappedArticleResponse {

    private StreamMapArticles streamMapArticles;

    public StreamMapArticles getStreamMapArticles() {
        return streamMapArticles;
    }

    public void setStreamMapArticles(StreamMapArticles streamMapArticles) {
        this.streamMapArticles = streamMapArticles;
    }
}

package com.ss.parlour.streamservice.utils.bean.requests;

import java.util.List;

public class ArticleToStreamRequest {

    private String streamName;
    private String userName;
    private List<String> articleIdList;

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

    public List<String> getArticleIdList() {
        return articleIdList;
    }

    public void setArticleIdList(List<String> articleIdList) {
        this.articleIdList = articleIdList;
    }
}

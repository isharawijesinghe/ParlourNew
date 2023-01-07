package com.ss.parlour.streamservice.utils.bean.requests;

import java.util.List;

public class ArticleToStreamRequest {

    private String streamId;
    private String userName;
    private List<String> articleIdList;

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

    public List<String> getArticleIdList() {
        return articleIdList;
    }

    public void setArticleIdList(List<String> articleIdList) {
        this.articleIdList = articleIdList;
    }
}

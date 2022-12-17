package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.Article;

import java.util.List;

public class ArticleHistoryResponseBean {

    private String articleId;
    private List<Article> articleHistoryList;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public List<Article> getArticleHistoryList() {
        return articleHistoryList;
    }

    public void setArticleHistoryList(List<Article> articleHistoryList) {
        this.articleHistoryList = articleHistoryList;
    }
}

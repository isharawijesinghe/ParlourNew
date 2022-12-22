package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("articleHistory")
public class ArticleHistory {

    @PrimaryKey
    private String articleId;
    private List<Article> oldArticles = new ArrayList<>();

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public List<Article> getOldArticles() {return oldArticles;}

    public void setOldArticles(List<Article> oldArticles) {
        this.oldArticles = oldArticles;
    }
}

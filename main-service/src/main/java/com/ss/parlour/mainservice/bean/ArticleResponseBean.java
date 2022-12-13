package com.ss.parlour.mainservice.bean;

import com.ss.parlour.mainservice.domain.Article;

import java.util.List;

public class ArticleResponseBean {
    private String narration;
    private List<Article> articleList;

    public ArticleResponseBean(){

    }

    public ArticleResponseBean(String narration){
        this.narration=narration;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}

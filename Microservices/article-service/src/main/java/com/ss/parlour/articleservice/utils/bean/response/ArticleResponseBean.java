package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Comment;

import java.util.ArrayList;
import java.util.List;

public class ArticleResponseBean {

    private Article article;
    private List<Comment> articleComments = new ArrayList<>();

    public List<Comment> getArticleComments() {
        return articleComments;
    }

    public void setArticleComments(List<Comment> articleComments) {
        this.articleComments = articleComments;
    }

    public Article getArticle() {return article;}

    public void setArticle(Article article) {this.article = article;}
}

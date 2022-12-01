package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.domain.cassandra.Article;

import java.util.Optional;

public interface ArticleDAOI {

    void saveArticle(Article article);
    Optional<Article> getArticleById(String articleId);
}

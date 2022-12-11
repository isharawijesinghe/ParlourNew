package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;

import java.util.Optional;

public interface ArticleDAOI {

    void saveArticle(Article article);
    Optional<Article> getArticleById(String articleId);
    Optional<LikeByArticle> getLikeByArticle(String articleId);
}

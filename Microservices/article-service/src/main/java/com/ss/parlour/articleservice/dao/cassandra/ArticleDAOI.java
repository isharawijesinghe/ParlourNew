package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;

import java.util.Optional;

public interface ArticleDAOI {

    void saveArticle(Article article);
    Optional<Article> getArticleById(String articleId);
    Optional<LikeByArticle> getLikeByArticle(String articleId);
    void updateArticleHistory(ArticleHistory articleHistory);
    Optional<ArticleHistory> getArticleHistoryByArticleId(String articleId);
    void saveArticleEditRequest(ArticleEditRequest articleEditRequest);
    void saveArticleEditRequestForUser(ArticleEditRequestForUser articleEditRequest);
    Optional<ArticleEditRequest> getArticleEditRequestForArticleId(String articleId);
    Optional<ArticleEditRequestForUser> getArticleEditRequestForUserId(String userId);
    void saveSharedArticles(SharedArticles sharedArticles);
    Optional<SharedArticles> getSharedArticlesForUserId(String userId);
}

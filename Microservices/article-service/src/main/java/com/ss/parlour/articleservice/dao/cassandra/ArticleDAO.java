package com.ss.parlour.articleservice.dao.cassandra;

import com.netflix.discovery.converters.Auto;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.repository.cassandra.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleDAO implements ArticleDAOI {

    @Autowired
    private ArticleRepositoryI articleRepositoryI;

    @Autowired
    private LikeByArticleRepositoryI likeByArticleRepositoryI;

    @Autowired
    private ArticleHistoryRepositoryI articleHistoryRepositoryI;

    @Autowired
    private ArticleEditRequestForUserRepositoryI articleEditRequestForUserRepositoryI;

    @Autowired
    private ArticleEditRequestRepositoryI articleEditRequestRepositoryI;

    @Autowired
    private SharedArticlesRepositoryI sharedArticlesRepositoryI;

    @Override
    public Optional<Article> getArticleById(String articleId){
        return articleRepositoryI.findById(articleId);
    }

    @Override
    public void saveArticle(Article article){
        articleRepositoryI.save(article);
    }

    @Override
    public Optional<LikeByArticle> getLikeByArticle(String articleId){return likeByArticleRepositoryI.findByArticleId(articleId);}

    @Override
    public void updateArticleHistory(ArticleHistory articleHistory){
        articleHistoryRepositoryI.save(articleHistory);
    }

    @Override
    public Optional<ArticleHistory> getArticleHistoryByArticleId(String articleId){return articleHistoryRepositoryI.findById(articleId);}

    @Override
    public void saveArticleEditRequest(ArticleEditRequest articleEditRequest){
        articleEditRequestRepositoryI.save(articleEditRequest);
    }

    @Override
    public void saveArticleEditRequestForUser(ArticleEditRequestForUser articleEditRequest){
        articleEditRequestForUserRepositoryI.save(articleEditRequest);
    }

    @Override
    public Optional<ArticleEditRequest> getArticleEditRequestForArticleId(String articleId){
        return articleEditRequestRepositoryI.findById(articleId);
    }

    @Override
    public Optional<ArticleEditRequestForUser> getArticleEditRequestForUserId(String userId){
        return articleEditRequestForUserRepositoryI.findById(userId);
    }

    @Override
    public void saveSharedArticles(SharedArticles sharedArticles){
        sharedArticlesRepositoryI.save(sharedArticles);
    }

    @Override
    public Optional<SharedArticles> getSharedArticlesForUserId(String userId){
        return sharedArticlesRepositoryI.findById(userId);
    }

}

package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.repository.cassandra.ArticleRepositoryI;
import com.ss.parlour.articleservice.repository.cassandra.LikeByArticleRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleDAO implements ArticleDAOI {

    @Autowired
    private ArticleRepositoryI articleRepositoryI;

    @Autowired
    private LikeByArticleRepositoryI likeByArticleRepositoryI;

    @Override
    public Optional<Article> getArticleById(String articleId){
        return articleRepositoryI.findById(articleId);
    }

    @Override
    public void saveArticle(Article article){
        articleRepositoryI.save(article);
    }

    @Override
    public Optional<LikeByArticle> getLikeByArticle(String articleId){
        return likeByArticleRepositoryI.findByArticleId(articleId);
    }


}

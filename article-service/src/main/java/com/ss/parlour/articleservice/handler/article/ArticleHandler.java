package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.dao.LikeDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private LikeDAOI likeDAOI;

    @Override
    public void handleArticleRequest(ArticleBean articleBean){
        Optional<Article> existingArticle = articleDAOI.getArticleById(articleBean.getId());
        Article article = populateArticle(articleBean);
        if (existingArticle.isPresent()){
            //Article update flow
            //--> Adding old one to history
            //-->Add audit trail

        }
        createArticle(article);
    }

    @Override
    public void handleLikeType(Like like){
        HashMap<String, Like> likeMap = new HashMap<>();
        Optional<LikeByArticle> existingLikeByArticleBean = articleDAOI.getLikeByArticle(like.getArticleId());
        if (existingLikeByArticleBean.isPresent()){
            likeMap =  existingLikeByArticleBean.get().getLikeMap();

        }
        likeMap.put(like.getKey(), like);
        LikeByArticle likeByArticle = new LikeByArticle();
        likeByArticle.setArticleId(like.getArticleId());
        likeByArticle.setLikeMap(likeMap);
    }


    protected void createArticle(Article article){
        articleDAOI.saveArticle(article);
    }

    protected Article populateArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setUserName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        return article;
    }



}

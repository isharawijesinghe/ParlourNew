package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.handler.LikeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

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
    public void handleLikeRequest(LikeBean likeBean) {

    }

    protected void createArticle(Article article){
        articleDAOI.saveArticle(article);
    }

    protected Article populateArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setAuthorName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        return article;
    }

}

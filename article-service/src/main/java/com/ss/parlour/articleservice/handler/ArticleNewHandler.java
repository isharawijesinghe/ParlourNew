package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleNewHandler implements ArticleNewHandlerI{

    @Autowired
    private ArticleDAOI articleDAOI;

    public void createArticle(ArticleRequestBean articleRequestBean){
        Article article = new Article();
        article.setId(articleRequestBean.getId());
        article.setAuthorName(articleRequestBean.getAuthorName());
        article.setTitle(articleRequestBean.getTitle());
        article.setSummary(articleRequestBean.getSummary());
        article.setContent(articleRequestBean.getContent());
        article.setCreatedDate(articleRequestBean.getCreatedDate());
        article.setModifiedDate(articleRequestBean.getModifiedDate());
        articleDAOI.saveArticle(article);
    }
}

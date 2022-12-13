package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.bean.ArticleResponseBean;
import com.ss.parlour.mainservice.handler.ArticleHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private ArticleHandlerI articleHandlerI;

    @Override
    public ArticleResponseBean create(ArticleRequestBean articleRequestBean) {
        return articleHandlerI.create(articleRequestBean);
    }

    @Override
    public ArticleResponseBean viewArticles(int channelId) {
        return articleHandlerI.viewArticles(channelId);
    }

    @Override
    public ArticleResponseBean viewArticles() {
        return articleHandlerI.viewArticles();
    }

    public ArticleHandlerI getArticleHandlerI() {
        return articleHandlerI;
    }

    public void setArticleHandlerI(ArticleHandlerI articleHandlerI) {
        this.articleHandlerI = articleHandlerI;
    }
}

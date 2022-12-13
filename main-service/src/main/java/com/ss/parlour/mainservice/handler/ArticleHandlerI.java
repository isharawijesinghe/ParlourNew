package com.ss.parlour.mainservice.handler;

import com.ss.parlour.mainservice.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.bean.ArticleResponseBean;
import com.ss.parlour.mainservice.domain.Article;

import java.util.List;

public interface ArticleHandlerI {
    public ArticleResponseBean create(ArticleRequestBean articleRequestBean);
    public ArticleResponseBean viewArticles(int channelId);
    public ArticleResponseBean viewArticles();
}

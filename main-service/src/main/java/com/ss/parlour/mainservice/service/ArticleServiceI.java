package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.bean.ArticleResponseBean;

public interface ArticleServiceI {
    public ArticleResponseBean create(ArticleRequestBean articleRequestBean);
    public ArticleResponseBean viewArticles(int channelId);
    public ArticleResponseBean viewArticles();
}

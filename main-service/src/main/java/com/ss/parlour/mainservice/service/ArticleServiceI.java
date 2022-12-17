package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.utils.bean.ArticleResponseBean;

public interface ArticleServiceI {
    public ArticleResponseBean create(ArticleRequestBean articleRequestBean);
    public ArticleResponseBean viewArticles(int channelId);
    public ArticleResponseBean viewArticles();
}

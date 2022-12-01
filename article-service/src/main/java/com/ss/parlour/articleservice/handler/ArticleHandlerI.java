package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.ArticleResponseBean;

public interface ArticleHandlerI {

    ArticleResponseBean handleArticleRequest(ArticleRequestBean articleRequestBean);
}

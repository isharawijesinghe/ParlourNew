package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.ArticleResponseBean;

public interface ArticleServiceI {

    ArticleResponseBean createArticle(ArticleRequestBean articleRequestBean);
}

package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;

public interface ArticleHandlerI {

    void handleArticleRequest(ArticleBean articleBean);
}

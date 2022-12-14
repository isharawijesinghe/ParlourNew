package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleDeleteRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleHistoryRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;

import java.util.Optional;

public interface ArticleHandlerI {

    Article handleArticleRequest(ArticleBean articleBean);
    void deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    Article findArticleDetailsById(String articleId);
}

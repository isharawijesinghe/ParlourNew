package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;

public interface ArticleHandlerI {

    Article processCreateArticleRequest(ArticleBean articleBean);
    void processDeleteArticleRequest(ArticleDeleteRequestBean articleDeleteRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean) throws Exception;
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    Article findArticleDetailsById(String articleId);
    String processArticleEditRequest(ArticleEditRequestBean articleEditRequestBean);
    void processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest);
}

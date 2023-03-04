package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface ArticleHandlerI {

    ArticleCommonResponse processCreateArticleRequest(ArticleBean articleBean);
    ArticleCommonResponse processDeleteArticleRequest(ArticleDeleteRequest articleDeleteRequest);
    ArticleResponse findArticleById(ArticleRequest articleRequest);
    ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest);
    Article findArticleDetailsById(String articleId);
    ArticleEditRequestResponse processArticleEditRequest(ArticleEditRequest articleEditRequest);
    ArticleEditApproveResponse processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest);
    ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest);
}

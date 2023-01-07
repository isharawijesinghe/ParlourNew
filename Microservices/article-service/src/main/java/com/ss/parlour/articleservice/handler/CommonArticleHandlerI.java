package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface CommonArticleHandlerI {

    ArticleCommonResponseBean handleArticleRequest(ArticleCreateRequestBean articleCreateRequestBean);
    CommentCommonResponseBean handleCommentRequest(CommentCreateRequestBean commentCreateRequestBean);
    LikeCommonResponseBean handleLikeRequest(LikeRequestBean likeRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    ArticleCommonResponseBean handleArticleDelete(ArticleDeleteRequestBean articleDeleteRequestBean);
    CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);
    Article findArticleDetailsById(String articleId);
}

package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;

public interface CommonArticleHandlerI {

    ArticleCommonResponseBean handleArticleRequest(ArticleCreateRequestBean articleCreateRequestBean);
    ArticleCommonResponseBean handleCommentRequest(CommentCreateRequestBean commentCreateRequestBean);
    ArticleCommonResponseBean handleLikeRequest(LikeRequestBean likeRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    ArticleCommonResponseBean handleArticleDelete(ArticleDeleteRequestBean articleDeleteRequestBean);
    ArticleCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);
}

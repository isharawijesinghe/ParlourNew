package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;

public interface CommonArticleHandlerI {

    ArticleCommonResponseBean handleArticleRequest(ArticleRequestBean articleRequestBean);
    ArticleCommonResponseBean handleCommentRequest(CommentRequestBean commentRequestBean);
    ArticleCommonResponseBean handleLikeRequest(LikeRequestBean likeRequestBean);
}

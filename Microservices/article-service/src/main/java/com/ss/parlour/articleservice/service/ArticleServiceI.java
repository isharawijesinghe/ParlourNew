package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;

public interface ArticleServiceI {

    ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean);
    ArticleCommonResponseBean createComment(CommentCreateRequestBean commentCreateRequestBean);
    ArticleCommonResponseBean addLike(LikeRequestBean likeRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean);
    ArticleCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);

}

package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;

public interface ArticleServiceI {

    ArticleCommonResponseBean createArticle(ArticleRequestBean articleRequestBean);
    ArticleCommonResponseBean createComment(CommentRequestBean commentRequestBean);
    ArticleCommonResponseBean addLike(LikeRequestBean likeRequestBean);
}

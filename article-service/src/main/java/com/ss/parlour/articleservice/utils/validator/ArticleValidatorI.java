package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;

public interface ArticleValidatorI {

    ArticleBean validateArticleRequest(ArticleRequestBean articleRequestBean);
    CommentBean validateCommentRequest(CommentRequestBean commentRequestBean);
    LikeBean validateArticleLikeRequest(LikeRequestBean likeRequestBean);
}

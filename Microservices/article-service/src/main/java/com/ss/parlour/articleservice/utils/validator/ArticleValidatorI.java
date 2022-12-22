package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;

public interface ArticleValidatorI {

    ArticleBean validateArticleRequest(ArticleCreateRequestBean articleCreateRequestBean);
    CommentBean validateCommentRequest(CommentCreateRequestBean commentCreateRequestBean);
    LikeBean validateArticleLikeRequest(LikeRequestBean likeRequestBean);
    void validateArticleDeleteRequest(ArticleDeleteRequestBean articleDeleteRequestBean);
    void validateCommentDeleteRequest(CommentDeleteRequestBean commentDeleteRequestBean);
}

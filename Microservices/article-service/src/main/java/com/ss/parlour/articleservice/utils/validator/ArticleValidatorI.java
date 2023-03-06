package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;

public interface ArticleValidatorI {

    ArticleBean validateArticleRequest(ArticleCreateRequest articleCreateRequest);
    CommentBean validateCommentRequest(CommentCreateRequest commentCreateRequest);
    LikeBean validateAddLikeRequest(LikeRequest likeRequest);
    void validateArticleDeleteRequest(ArticleDeleteRequest articleDeleteRequest);
    void validateCommentDeleteRequest(CommentDeleteRequest commentDeleteRequest);
}

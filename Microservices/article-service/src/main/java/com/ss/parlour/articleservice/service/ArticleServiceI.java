package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface ArticleServiceI {

    ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean);
    CommentCommonResponseBean addComment(CommentCreateRequestBean commentCreateRequestBean);
    LikeCommonResponseBean addLike(LikeRequestBean likeRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    Article findArticleDetailsById(String articleId);
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean);
    CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);
    ArticleEditRequestResponse articleEditRequest(ArticleEditRequestBean articleEditRequestBean);
    ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest);


}

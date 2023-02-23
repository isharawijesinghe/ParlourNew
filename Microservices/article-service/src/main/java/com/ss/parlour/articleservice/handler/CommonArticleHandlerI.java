package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface CommonArticleHandlerI {

    ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean);
    CommentCommonResponseBean addComment(CommentCreateRequestBean commentCreateRequestBean);
    LikeCommonResponseBean addLike(LikeRequestBean likeRequestBean);
    ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean);
    ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean);
    ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean);
    CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);
    Article findArticleDetailsById(String articleId);
    ArticleEditRequestResponse articleEditRequest(ArticleEditRequestBean articleEditRequestBean);
    ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest);
}

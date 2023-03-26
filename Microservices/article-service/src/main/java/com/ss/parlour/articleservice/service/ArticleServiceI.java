package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface ArticleServiceI {

    ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest);
    ArticleResponse addComment(CommentCreateRequest commentCreateRequest);
    ArticleResponse addLike(LikeRequest likeRequest);
    ArticleResponse findArticleById(String articleId);
    ArticleResponse findArticleByUser(ArticleListRequest articleListRequest);
    ArticleResponse findArticleDetailsById(String articleId);
    ArticleResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest);
    ArticleResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest);
    ArticleResponse findArticleComments(CommentRequest commentRequest);
    ArticleResponse deleteComment(CommentDeleteRequest commentDeleteRequest);
    ArticleResponse articleEditRequest(ArticleEditRequest articleEditRequest);
    ArticleResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest);
    ArticleResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest);
    ArticleResponse addTopic(TopicAddRequest topicAddRequest);
    ArticleResponse findAllTopic();


}

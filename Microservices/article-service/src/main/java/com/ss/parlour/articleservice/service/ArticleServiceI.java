package com.ss.parlour.articleservice.service;


import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface ArticleServiceI {

    ArticleCommonResponse createArticle(ArticleCreateRequest articleCreateRequest);
    CommentCommonResponse addComment(CommentCreateRequest commentCreateRequest);
    LikeCommonResponse addLike(LikeRequest likeRequest);
    ArticleResponse findArticleById(ArticleRequest articleRequest);
    Article findArticleDetailsById(String articleId);
    ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest);
    ArticleCommonResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest);
    CommentCommonResponse deleteComment(CommentDeleteRequest commentDeleteRequest);
    ArticleEditRequestResponse articleEditRequest(ArticleEditRequest articleEditRequest);
    ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest);
    ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest);
    TopicAddResponse addTopic(TopicAddRequest topicAddRequest);
    TopicResponse findAllTopic();


}

package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ArticleDAOI {

    Optional<Article> getArticleById(String articleId);
    Optional<SharedArticles> getSharedArticle(String editRequestId, String articleId);
    Optional<EditDraftArticles> getDraftedEditArticle(String editRequestId, String articleId);
    Optional<EditRequestByArticle> getArticleEditRequestForArticleId(String articleId);
    Optional<EditRequestByUser> getArticleEditRequestForUserId(String userId);
    void saveArticleEditRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    void saveArticleApprovalRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    Optional<EditRequest> getArticleEditRequest(String editRequestId, String articleId);
    void saveArticleEditDraftRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    void saveArticleEditPublishRequest(ArticleEditPublishHelperBean articleEditPublishHelperBean);
    void saveArticleCreateRequest(ArticleHandlerHelperBean articleHandlerHelperBean);
    void saveTopic(List<Topics> topics);
    Optional<List<Topics>> loadAllTopicsEntries();
    Optional<List<ArticleByUser>> getArticleByUserId(String userId);
    Optional<ArticleByUser> getArticleByUserIdAndArticleId(String userId, String articleId);
    void deleteArticleEntry(ArticleHandlerHelperBean articleHandlerHelperBean);
    Optional<LikeByArticle> getLikeByArticleEntry(String articleId, String userId);
    Optional<LikeByArticleGroup> getLikeByArticleGroupEntry(String articleId, String userId, Timestamp createdDate);
    void updateArticleUserLikeRequest(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean);
    Optional<List<SharedArticlesWithUser>> getSharedArticlesWithUser(String userId);
    Optional<SharedArticlesWithUser> getSharedArticlesWithUser(String userId, String articleId);
    Optional<List<ArticleContributors>> getArticleContributorsList(String articleId);
}

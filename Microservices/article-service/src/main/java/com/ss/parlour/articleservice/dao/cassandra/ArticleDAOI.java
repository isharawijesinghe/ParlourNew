package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleHandlerHelperBean;
import com.ss.parlour.articleservice.utils.bean.ArticleLikeHandlerHelperBean;
import com.ss.parlour.articleservice.utils.bean.EditRequestHandlerHelperBean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ArticleDAOI {

    Optional<Article> getArticleById(String articleId);
    Optional<SharedArticles> getSharedArticle(String articleId, String requestId);
    Optional<EditRequestByArticle> getArticleEditRequestForArticleId(String articleId);
    Optional<EditRequestByUser> getArticleEditRequestForUserId(String userId);
    void saveArticleEditRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    void saveArticleApprovalRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    Optional<EditRequest> getArticleEditRequest(String editRequestId, String articleId);
    void saveArticleEditDraftRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean);
    void saveArticleCreateRequest(ArticleHandlerHelperBean articleHandlerHelperBean);
    void saveTopic(List<Topics> topics);
    Optional<List<Topics>> loadAllTopicsEntries();
    Optional<ArticleByUser> getArticleByUserId(String userId);
    void deleteArticleEntry(ArticleHandlerHelperBean articleHandlerHelperBean);
    Optional<LikeByArticle> getLikeByArticleEntry(String articleId, String userId);
    Optional<LikeByArticleGroup> getLikeByArticleGroupEntry(String articleId, String userId, Timestamp createdDate);
    void updateArticleUserLikeRequest(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean);
}

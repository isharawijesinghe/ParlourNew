package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.ArticleUpdateHelperBean;
import com.ss.parlour.articleservice.utils.bean.EditRequestHelperBean;
import org.springframework.data.cassandra.core.CassandraBatchOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ArticleDAOI {

    void saveArticle(Article article);
    Optional<Article> getArticleById(String articleId);
    Optional<LikeByArticle> getLikeByArticle(String articleId);
    void updateArticleHistory(ArticleHistory articleHistory);
    Optional<ArticleHistory> getArticleHistoryByArticleId(String articleId);
    void saveArticleEditRequest(EditRequestByArticle editRequestByArticle);
    void saveArticleEditRequestForUser(EditRequestByUser articleEditRequest);
    Optional<EditRequestByArticle> getArticleEditRequestForArticleId(String articleId);
    Optional<EditRequestByUser> getArticleEditRequestForUserId(String userId);
    void saveSharedArticles(SharedArticles sharedArticles);
    Optional<SharedArticles> getSharedArticlesForUserId(String userId);
    void saveArticleEditRequest(EditRequestHelperBean editRequestHelperBean);
    void saveArticleApprovalRequest(EditRequestHelperBean editRequestHelperBean);
    Optional<EditRequest> getArticleEditRequest(String editRequestId);
    Optional<EditDraftArticles> getEditDraftArticleByArticleId(String articleId);
    void saveArticleEditDraftRequest(EditRequestHelperBean editRequestHelperBean);
    void saveArticleCreateRequest(ArticleUpdateHelperBean articleUpdateHelperBean);
    void saveTopic(List<Topics> topics);
    Optional<List<Topics>> loadAllTopicsEntries();
}

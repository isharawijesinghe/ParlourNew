package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleEditPublishHelperBean;
import com.ss.parlour.articleservice.utils.bean.ArticleHandlerHelperBean;
import com.ss.parlour.articleservice.utils.bean.EditRequestHandlerHelperBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

public interface ArticleHandlerI {

    ArticleListResponse findArticleByUser(ArticleListRequest articleListRequest);
    ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest);
    void prePopulatedForNewArticleFlow(ArticleBean articleBean);
    void prePopulateForUpdateArticleFlow(ArticleBean articleBean);
    ArticleHandlerHelperBean populateArticleCreateHandlerHelperBean(ArticleBean articleBean);
    void populateArticleDetailsFromDb(ArticleDetailsResponse articleDetailsResponse, String articleId);
    void populateArticleAuthorDetails(ArticleDetailsResponse articleDetailsResponse);
    void populateArticleContributorsDetails(ArticleDetailsResponse articleDetailsResponse,String articleId);
    ArticleHandlerHelperBean populateArticleDeleteHelperBean(ArticleDeleteRequest articleDeleteRequest);
    EditRequestHandlerHelperBean populateArticleEditRequestHelperBean(ArticleEditRequest articleEditRequest);
    EditRequestHandlerHelperBean populateArticleEditRequestApprovalHelperBean(ArticleEditApproveRequest articleEditApproveRequest);
    EditRequestHandlerHelperBean populateSaveArticleEditDraftRequestHelperBean(ArticleEditDraftRequest articleEditDraftRequest);
    ArticleEditPublishHelperBean populatePublishArticleEditDraftRequestHelperBean(ArticlePublishEditDraftRequest articlePublishEditDraftRequest);
    ArticleEditShareUserResponse findShareArticlesByUser(ArticleEditShareUserRequest articleEditShareUserRequest);
    TopicResponse findAllTopic();
}

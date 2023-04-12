package com.ss.parlour.articleservice.handler.article;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.common.ObjectHelper;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.common.DateUtils;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import com.ss.parlour.articleservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    @Autowired
    private ObjectHelper objectHelper;

    @Override
    public void addLikeRequest(LikeRequestBean likeRequestBean){
        ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean = new ArticleLikeHandlerHelperBean();
        likeRequestBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        //1. Load current user like for article
        //2. Load article like group
        populateCurrentUserArticleLikeStatus(articleLikeHandlerHelperBean, likeRequestBean);
        //1. Populate current user like for article
        //2. Populate article like group
        populateNewArticleUserLikeDate(articleLikeHandlerHelperBean, likeRequestBean);
        articleDAOI.updateArticleUserLikeRequest(articleLikeHandlerHelperBean);
    }

    @Override
    public ArticleListResponse findArticleByUser(ArticleListRequest articleListRequest){
        ArticleListResponse  articleListResponse = new ArticleListResponse();
        ArticleListRequest.ArticleListRequestInner articleListRequestInner = articleListRequest.getArticleListRequestInner();
        Optional<List<ArticleByUser>> currentUserArticleFromDb = articleDAOI.getArticleByUserId(articleListRequestInner.getUserId());
        currentUserArticleFromDb.ifPresent(userArticle -> {articleListResponse.setArticleResponseList(userArticle);});
        //todo --> need to add pagination
        return articleListResponse;
    }

    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = new ArticleHistoryResponse();
        //Optional<ArticleHistory> articleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequest.getArticleId());
        //articleHistoryInDb.ifPresent((articleHistory -> articleHistoryResponse.setArticleHistoryList(articleHistory.getOldArticles())));
        return articleHistoryResponse;
    }

    @Override
    public ArticleEditShareUserResponse findShareArticlesByUser(ArticleEditShareUserRequest articleEditShareUserRequest){
        ArticleEditShareUserResponse articleEditShareUserResponse = new ArticleEditShareUserResponse();
        Optional<List<SharedArticlesWithUser>> sharedArticlesWithUsersOptional = articleDAOI.getSharedArticlesWithUser(articleEditShareUserRequest.getArticleEditShareUserRequestInner().getUserId());
        var sharedArticleWithUser = sharedArticlesWithUsersOptional.
                orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.NO_ARTICLE_FOUND_SHARE_WITH_USER + articleEditShareUserRequest.getArticleEditShareUserRequestInner().getUserId()));
        articleEditShareUserResponse.setSharedArticlesWithUsers(sharedArticleWithUser);
        return articleEditShareUserResponse;
    }

    @Override
    public TopicResponse findAllTopic(){
        TopicResponse topicResponse = new TopicResponse();
        Optional<List<Topics>> topicsList = articleDAOI.loadAllTopicsEntries();
        topicsList.ifPresent(topics -> topicResponse.setTopicName(topics));
        return topicResponse;
    }

    @Override
    public ArticleHandlerHelperBean populateArticleCreateHandlerHelperBean(ArticleBean articleBean){
        ArticleHandlerHelperBean articleHandlerHelperBean = new ArticleHandlerHelperBean();


        populateNewArticle(articleHandlerHelperBean, articleBean);
        populateNewUserAssignArticle(articleHandlerHelperBean, articleBean);
        return articleHandlerHelperBean;
    }

    @Override
    public ArticleHandlerHelperBean populateArticleDeleteHelperBean(ArticleDeleteRequest articleDeleteRequest){
        ArticleHandlerHelperBean articleHandlerHelperBean = new ArticleHandlerHelperBean();

        populateDeleteArticle(articleHandlerHelperBean, articleDeleteRequest);
        populateDeleteArticleByUser(articleHandlerHelperBean, articleDeleteRequest);
        populateDeleteArticleHistory(articleHandlerHelperBean, articleDeleteRequest);
        return articleHandlerHelperBean;
    }

    @Override
    public EditRequestHandlerHelperBean populateArticleEditRequestHelperBean(ArticleEditRequest articleEditRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = new EditRequestHandlerHelperBean();
        //Create new edit request
        EditRequest articleEditBean = createNewArticleEditRequest(articleEditRequest, ArticleConst.ARTICLE_EDIT_REQUEST_PENDING);

        //1. Update edit request --> Changing status
        //2. Populate edit request by article after approval
        //3. Populate edit request by user after approval
        populateArticleEditRequest(articleEditBean, editRequestHandlerHelperBean);
        populateArticleEditRequestByArticle(editRequestHandlerHelperBean);
        populateArticleEditRequestByUser(editRequestHandlerHelperBean);
        return editRequestHandlerHelperBean;
    }

    @Override
    public EditRequestHandlerHelperBean populateArticleEditRequestApprovalHelperBean(ArticleEditApproveRequest articleEditApproveRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = new EditRequestHandlerHelperBean();
        //Load article edit request from db
        EditRequest editRequest = populateEditRequestBeanFromDb(articleEditApproveRequest.getArticleEditApproveRequestInner().getEditRequestId(),
                articleEditApproveRequest.getArticleEditApproveRequestInner().getArticleId());

        //1. Update edit request --> Changing status
        //2. Populate edit request by article after approval
        //3. Populate edit request by user after approval
        prePopulateEditRequestForApproval(editRequest, ArticleConst.ARTICLE_EDIT_REQUEST_APPROVED);
        populateArticleEditRequest(editRequest, editRequestHandlerHelperBean);
        populateArticleEditRequestByArticle(editRequestHandlerHelperBean);
        populateArticleEditRequestByUser(editRequestHandlerHelperBean);

        //1. Populate shared article data bean
        //2. Populate shared article with user data bean
        populateNewSharedArticle(editRequestHandlerHelperBean, ArticleConst.SHARED_WITH_USER_PENDING);
        populateSharedArticleWithUser(editRequestHandlerHelperBean, ArticleConst.SHARED_WITH_USER_PENDING);

        return editRequestHandlerHelperBean;
    }

    @Override
    public EditRequestHandlerHelperBean populateSaveArticleEditDraftRequestHelperBean(ArticleEditDraftRequest articleEditDraftRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = new EditRequestHandlerHelperBean();

        //Load article edit request from db
        EditRequest editRequest = populateEditRequestBeanFromDb( articleEditDraftRequest.getArticleEditDraftRequestInner().getEditRequestId(),
                articleEditDraftRequest.getArticleEditDraftRequestInner().getArticleId());

        //1. Populate shared article data bean
        //2. Populate shared article with user data bean
        SharedArticles sharedArticles = populateSharedArticlesEditArticleDraft(editRequest, ArticleConst.SHARED_WITH_USER_ARTICLE_DRAFT);
        SharedArticlesWithUser sharedArticlesWithUser = populateSharedArticlesWithUserEditArticleDraft(editRequest, ArticleConst.SHARED_WITH_USER_ARTICLE_DRAFT);
        editRequestHandlerHelperBean.setSharedArticles(sharedArticles);
        editRequestHandlerHelperBean.setSharedArticlesWithUser(sharedArticlesWithUser);

        //1. Populate article edit draft data
        populateEditArticleDraft(articleEditDraftRequest.getArticleEditDraftRequestInner() , editRequestHandlerHelperBean);

        return editRequestHandlerHelperBean;
    }

    @Override
    public ArticleEditPublishHelperBean populatePublishArticleEditDraftRequestHelperBean(ArticlePublishEditDraftRequest articlePublishEditDraftRequest){
        ArticleEditPublishHelperBean articleEditPublishHelperBean = new ArticleEditPublishHelperBean();

        //Load article edit request from db
        EditRequest editRequest = populateEditRequestBeanFromDb( articlePublishEditDraftRequest.getArticlePublishEditDraftInner().getEditRequestId(),
                articlePublishEditDraftRequest.getArticlePublishEditDraftInner().getArticleId());

        //Load current article from db
        Article currentArticle = populateCurrentArticleBeanFromDb(articlePublishEditDraftRequest.getArticlePublishEditDraftInner().getArticleId());

        //Load saved edit article draft from db
        EditDraftArticles editDraftArticles = populateDraftedEditArticle(articlePublishEditDraftRequest.getArticlePublishEditDraftInner().getEditRequestId(),
                articlePublishEditDraftRequest.getArticlePublishEditDraftInner().getArticleId());

        //1. Populate shared article data bean
        //2. Populate shared article with user data bean
        SharedArticles sharedArticles =  populateSharedArticlesEditArticleDraft(editRequest, ArticleConst.SHARED_WITH_USER_ARTICLE_DRAFT);
        SharedArticlesWithUser sharedArticlesWithUser =  populateSharedArticlesWithUserEditArticleDraft(editRequest, ArticleConst.SHARED_WITH_USER_ARTICLE_DRAFT);
        articleEditPublishHelperBean.setSharedArticles(sharedArticles);
        articleEditPublishHelperBean.setSharedArticlesWithUser(sharedArticlesWithUser);

        //1.Update old article with modified values
        //2.Populate article by user values
        //2.Populate article contributors
        populateUpdatedArticle(articleEditPublishHelperBean, currentArticle, editDraftArticles);
        populateUpdatedArticleByUser(articleEditPublishHelperBean);
        populateArticleContributors(articleEditPublishHelperBean);

        return articleEditPublishHelperBean;

    }

    @Override
    //Populate article details to send response back to browser
    public void populateArticleDetailsFromDb(ArticleDetailsResponse articleDetailsResponse, String articleId){
        Article article = populateCurrentArticleBeanFromDb(articleId);
        articleDetailsResponse.setArticle(article);
    }

    @Override
    public void populateArticleAuthorDetails(ArticleDetailsResponse articleDetailsResponse) {
        Article article = articleDetailsResponse.getArticle();
        ResponseEntity<ArticleResponse> authorDetailResponseBean = (ResponseEntity<ArticleResponse>) findUserDetails(article.getUserId());
        ArticleResponse articleResponse = authorDetailResponseBean.getBody();
        if (articleResponse.getHttpStatus() != 200){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.AUTHOR_NOT_FOUND_ERROR + article.getUserId());
        }
        UserInfoResponseBean userInfoResponseBean =  objectHelper.getObjectMapper().convertValue(articleResponse.getBody(), new TypeReference<UserInfoResponseBean>() {});
        articleDetailsResponse.setAuthorDetails(new UserDetailResponse(userInfoResponseBean));
    }

    @Override
    public void populateArticleContributorsDetails(ArticleDetailsResponse articleDetailsResponse,String articleId){
        List<UserDetailResponse> contributors = new ArrayList<>();
        Optional<List<ArticleContributors>> articleContributorsOptional = articleDAOI.getArticleContributorsList(articleId);
        articleContributorsOptional.ifPresent(
                articleContributorsList -> {
                    articleContributorsList.forEach(
                            articleContributors -> {
                                UserDetailResponse returnedUserDetailResponse = resolveContributor(articleContributors);
                                if (returnedUserDetailResponse != null){
                                    contributors.add(returnedUserDetailResponse);
                                }
                            }
                    );
                }
        );
        articleDetailsResponse.setContributors(contributors);
    }

    @Override
    public void prePopulatedForNewArticleFlow(ArticleBean articleBean){
        articleBean.setArticleId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        articleBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    @Override
    public void prePopulateForUpdateArticleFlow(ArticleBean articleBean){
        var currentArticle =  populateCurrentArticleBeanFromDb(articleBean.getArticleId());
        articleBean.setCreatedDate(currentArticle.getCreatedDate());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }


    protected SharedArticles populateSharedArticle(String editRequestId, String articleId){
        Optional<SharedArticles> sharedArticlesOptional = articleDAOI.getSharedArticle(editRequestId, articleId);
        var sharedArticleOld = sharedArticlesOptional.orElseThrow();
        return sharedArticleOld;
    }

    protected SharedArticles populateSharedArticlesEditArticleDraft(EditRequest editRequest, String status){
        SharedArticles sharedArticles = populateSharedArticle(editRequest.getEditRequestId(), editRequest.getArticleId());
        sharedArticles.setStatus(status);
        return sharedArticles;
    }

    protected SharedArticlesWithUser populateSharedArticlesWithUserEditArticleDraft(EditRequest editRequest, String status){
        SharedArticlesWithUser sharedArticlesWithUser = populateSharedArticleWithUser(editRequest.getEditRequestId(), editRequest.getArticleId());
        sharedArticlesWithUser.setStatus(status);
        return sharedArticlesWithUser;
    }

    protected SharedArticlesWithUser populateSharedArticleWithUser(String editRequestId, String articleId){
        Optional<SharedArticlesWithUser> sharedArticlesWithUserOptional = articleDAOI.getSharedArticlesWithUser(editRequestId, articleId);
        var sharedArticlesWithUserOld = sharedArticlesWithUserOptional.orElseThrow();
        return sharedArticlesWithUserOld;
    }

    protected Article populateCurrentArticleBeanFromDb(String articleId){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleId);
        var currentArticle = currentArticleInDb.orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_NOT_FOUND_ERROR + articleId));
        return currentArticle;
    }

    protected void populateNewArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleBean articleBean){
        Article article = new Article(articleBean);//Create article bean
        articleHandlerHelperBean.setUpdatedArticle(article);
    }

    protected void populateNewUserAssignArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleBean articleBean){
        ArticleByUser articleByUser = new ArticleByUser(articleBean);
        articleHandlerHelperBean.setArticleByUser(articleByUser);
    }

    protected void populateDeleteArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleDeleteRequest.getArticleDeleteRequestInner().getArticleId());
        currentArticleInDb.ifPresent((article) -> articleHandlerHelperBean.setUpdatedArticle(article));
    }

    protected void populateDeleteArticleByUser(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<ArticleByUser> currentArticleByUserInDb = articleDAOI.getArticleByUserIdAndArticleId(
                articleDeleteRequest.getArticleDeleteRequestInner().getUserId(), articleDeleteRequest.getArticleDeleteRequestInner().getArticleId());
        currentArticleByUserInDb.ifPresent((articleByUser) -> articleHandlerHelperBean.setArticleByUser(articleByUser));
    }

    protected void populateDeleteArticleHistory(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){}

    protected void populateCurrentUserArticleLikeStatus(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean, LikeRequestBean likeRequestBean){
        Optional<LikeByArticle> likeByArticle = articleDAOI.getLikeByArticleEntry(likeRequestBean.getArticleId(), likeRequestBean.getUserId());
        likeByArticle.ifPresent(lba -> {
            articleLikeHandlerHelperBean.setCurrentLikeByArticle(lba);
            Optional<LikeByArticleGroup> likeByArticleGroup = articleDAOI.getLikeByArticleGroupEntry(lba.getArticleId(), lba.getUserId(), lba.getCreatedDate());
            likeByArticleGroup.ifPresent(lbag -> {articleLikeHandlerHelperBean.setCurrentLikeByArticleGroup(lbag);});
        });
    }

    protected void populateNewArticleUserLikeDate(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean, LikeRequestBean likeRequestBean){
        if (likeRequestBean.getStatus() == ArticleConst.USER_LIKED || likeRequestBean.getStatus() == ArticleConst.USER_UNLIKED){
            articleLikeHandlerHelperBean.setNewLikeByArticle(new LikeByArticle(likeRequestBean));
            articleLikeHandlerHelperBean.setNewLikeByArticleGroup(new LikeByArticleGroup(likeRequestBean));
        }
    }

    protected EditRequest createNewArticleEditRequest(ArticleEditRequest articleEditRequest, String status){
        EditRequest articleEditBean = articleEditRequest.getArticleEditRequest();
        String articleEditRequestId = keyGenerator.articleEditRequestGenerator(articleEditBean.getArticleId(), articleEditBean.getRequesterId());
        articleEditBean.setEditRequestId(articleEditRequestId); //Update article edit request id (~Generate)
        articleEditBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        articleEditBean.setModifiedDate(articleEditBean.getModifiedDate());
        articleEditBean.setEditRequestStatus(status); //Update article edit request status to pending
        return articleEditBean;
    }

    protected EditRequest populateEditRequestBeanFromDb(String editRequestId, String articleId)  {
        var currentEditRequest = articleDAOI.getArticleEditRequest(editRequestId, articleId)
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_EDIT_REQ_NOT_FOUND_ERROR + editRequestId));
        return currentEditRequest;
    }

    protected void prePopulateEditRequestForApproval(EditRequest editRequest, String status){
        editRequest.setEditRequestStatus(status);
        editRequest.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void populateArticleEditRequest(EditRequest editRequest, EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        editRequestHandlerHelperBean.setEditRequest(editRequest);
    }

    protected void populateArticleEditRequestByArticle(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequestHandlerHelperBean.setEditRequestByArticle(new EditRequestByArticle(editRequest));
    }

    protected void populateArticleEditRequestByUser(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequestHandlerHelperBean.setEditRequestByUser(new EditRequestByUser(editRequest));
    }

    protected void populateNewSharedArticle(EditRequestHandlerHelperBean editRequestHandlerHelperBean, String status){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequestHandlerHelperBean.setSharedArticles(new SharedArticles(editRequest, status));
    }

    protected void populateSharedArticleWithUser(EditRequestHandlerHelperBean editRequestHandlerHelperBean, String status){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequestHandlerHelperBean.setSharedArticlesWithUser(new SharedArticlesWithUser(editRequest, status));
    }

    protected ResponseEntity<?> findUserDetails(String userId){
        return externalRestWriterI.findUserInfoDetailsById(userId);
    }

    protected void populateEditArticleDraft(ArticleEditDraftRequest.ArticleEditDraftRequestInner articleEditDraftRequestInner, EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        editRequestHandlerHelperBean.setEditDraftArticles(new EditDraftArticles(articleEditDraftRequestInner));
    }

    protected EditDraftArticles populateDraftedEditArticle(String editRequestId, String articleId){
        Optional<EditDraftArticles> editDraftArticlesOptional = articleDAOI.getDraftedEditArticle(editRequestId, articleId);
        var editDraftArticles = editDraftArticlesOptional
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.EDIT_DRAFT_ARTICLE_NOT_FOUND + editRequestId));
        return editDraftArticles;
    }

    protected void populateUpdatedArticle(ArticleEditPublishHelperBean articleEditPublishHelperBean, Article oldArticle, EditDraftArticles editDraftArticles){
        Article updatedArticle = oldArticle;
        updatedArticle.setTitle(editDraftArticles.getTitle());
        updatedArticle.setSummary(editDraftArticles.getSummary());
        updatedArticle.setContent(editDraftArticles.getContent());
        updatedArticle.setThumbnailUrl(editDraftArticles.getThumbnailUrl());
        updatedArticle.setCategoryId(editDraftArticles.getCategoryId());
        updatedArticle.setModifiedDate(DateUtils.currentSqlTimestamp());
        articleEditPublishHelperBean.setUpdatedArticle(updatedArticle);
    }

    protected UserDetailResponse resolveContributor(ArticleContributors articleContributors){
        UserInfoResponseBean userInfoResponseBean = null;
        ResponseEntity<ArticleResponse> authorDetailResponseBean = (ResponseEntity<ArticleResponse>) findUserDetails(articleContributors.getContributorId());
        ArticleResponse articleResponse = authorDetailResponseBean.getBody();
        if (articleResponse.getHttpStatus() == 200){
            userInfoResponseBean =  objectHelper.getObjectMapper().convertValue(articleResponse.getBody(), new TypeReference<UserInfoResponseBean>() {});
            return new UserDetailResponse(userInfoResponseBean);
        }
        return null;
    }

    protected void populateArticleContributors(ArticleEditPublishHelperBean articleEditPublishHelperBean){
        EditDraftArticles editDraftArticles = articleEditPublishHelperBean.getEditDraftArticles();
        articleEditPublishHelperBean.setArticleContributors(new ArticleContributors(editDraftArticles));
    }

    protected void populateUpdatedArticleByUser(ArticleEditPublishHelperBean articleEditPublishHelperBean){
        Article article = articleEditPublishHelperBean.getUpdatedArticle();
        articleEditPublishHelperBean.setArticleByUser(new ArticleByUser(article));
    }


}

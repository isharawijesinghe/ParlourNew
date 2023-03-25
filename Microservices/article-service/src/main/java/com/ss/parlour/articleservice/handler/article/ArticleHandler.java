package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.common.DateUtils;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import com.ss.parlour.articleservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    /***
     * Handle when user request post create
     * 1. Check request has article id -> If not then it is new article create request
     * 2. If request has article id then article edit or update request -> Then keep history and update new request
     * @param articleBean
     * @return
     */
    @Override
    public String processCreateArticleRequest(ArticleBean articleBean){
        ArticleHandlerHelperBean articleHandlerHelperBean = new ArticleHandlerHelperBean();
        if (articleBean.getArticleId() == null || articleBean.getArticleId().isEmpty()){ //New article request
            prePopulatedForNewArticleFlow(articleBean);
        } else { //Article update approve request flow
            prePopulateForUpdateArticleFlow(articleBean, articleHandlerHelperBean);
        }
        populateUpdateArticle(articleHandlerHelperBean, articleBean);//Populate article bean
        populateUserAssignArticle(articleHandlerHelperBean, articleBean);//Populate article by user bean
        //1. Update Article table
        //2. Update article by user table
        articleDAOI.saveArticleCreateRequest(articleHandlerHelperBean);
        return articleBean.getArticleId();
    }

    /***
     * Update saved article object in db >> Update Article table
     * Handle when user add post like
     * @param likeRequestBean
     */
    @Override
    public void addLikeRequest(LikeRequestBean likeRequestBean){
        ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean = new ArticleLikeHandlerHelperBean();
        likeRequestBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        populateCurrentUserArticleLikeStatus(articleLikeHandlerHelperBean, likeRequestBean);
        populateNewArticleUserLikeDate(articleLikeHandlerHelperBean, likeRequestBean);
        articleDAOI.updateArticleUserLikeRequest(articleLikeHandlerHelperBean);
    }

    /***
     * 1. If delete request article exists --> Then update article status to inactive
     * 2. If required can be removed entry from list as well
     * Handle when user delete an article
     * @param articleDeleteRequest
     */
    @Override
    public void processDeleteArticleRequest(ArticleDeleteRequest articleDeleteRequest){
        ArticleHandlerHelperBean articleHandlerHelperBean = new ArticleHandlerHelperBean();
        //1. Delete article entry
        //2. Delete article entry by user
        //3. Delete article history entry
        populateDeleteArticle(articleHandlerHelperBean, articleDeleteRequest);
        populateDeleteArticleByUser(articleHandlerHelperBean, articleDeleteRequest);
        populateDeleteArticleHistory(articleHandlerHelperBean, articleDeleteRequest);
        articleDAOI.deleteArticleEntry(articleHandlerHelperBean);
    }


    /***
     * When user request to find article by id
     * @param articleId
     * @return articleResponse
     */
    @Override
    public ArticleResponse findArticleById(String articleId){
        ArticleResponse articleResponse = new ArticleResponse();
        //1. Populate article details
        //2. Populate article author details
        //3. Populate article comments
        populateArticleDetailsFromDb(articleResponse, articleId);
        populateArticleAuthorDetails(articleResponse);
        return articleResponse;
    }

    /***
     * Query for user created articles and return them back to server
     * @param articleListRequest
     * @return List<Article>
     */
    @Override
    public List<Article> findArticleByUser(ArticleListRequest articleListRequest){
        AtomicReference<List<Article>> currentUserAssignArticle = new AtomicReference<>();
        Optional<ArticleByUser> currentUserArticleFromDb = articleDAOI.getArticleByUserId(articleListRequest.getLoginName());
//        currentUserArticleFromDb.ifPresent(
//                userArticle -> currentUserAssignArticle.set(new ArrayList<>(userArticle.getUserCreatedArticles().values()))
//        );
        return currentUserAssignArticle.get();
    }

    /***
     * When user request to find article history by id
     * @param articleHistoryRequest
     * @return articleHistoryResponse
     */
    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = new ArticleHistoryResponse();
        //Optional<ArticleHistory> articleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequest.getArticleId());
        //articleHistoryInDb.ifPresent((articleHistory -> articleHistoryResponse.setArticleHistoryList(articleHistory.getOldArticles())));
        return articleHistoryResponse;
    }

    /***
     * Find user article by id
     * @param articleId
     * @return Article
     */
    @Override
    public Article findArticleDetailsById(String articleId){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleId);
        var articleReturned = currentArticleInDb.
                orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_DETAIL_FOUND_ERROR + articleId));
        return articleReturned;
    }

    /***
     * Process article edit request
     * 1. Populate edit request
     * 2. Populate edit request by article
     * 3. Populate edit request by user
     * 4. Save request into tables
     * @param articleEditRequest
     * @return ArticleEditRequestResponse
     */
    @Override
    public String processArticleEditRequest(ArticleEditRequest articleEditRequest){
        EditRequest articleEditBean = prePopulateArticleEditRequest(articleEditRequest);
        EditRequestHandlerHelperBean editRequestHandlerHelperBean =  populateEditRequestHelperBean(articleEditBean);//Populate article edit request
        populateEditRequestByArticle(editRequestHandlerHelperBean, articleEditBean);//Populate article edit request for article
        populateEditRequestByUser(editRequestHandlerHelperBean, articleEditBean);//Populate article  edit request for user
        articleDAOI.saveArticleEditRequest(editRequestHandlerHelperBean);//Save entries into db
        return articleEditBean.getArticleId();
    }

    /***
     * Process article edit request approval
     * 1. Populate edit request by loading from db
     * 2. Populate edit request by article by loading from db + updating edit request bean
     * 3. Populate edit request by user by loading from db + updating edit request bean
     * 4. Save request into tables
     * @param articleEditApproveRequest
     */
    @Override
    public void processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean =  populateEditRequestHelperBeanFromDb(articleEditApproveRequest.getEditRequestId(), articleEditApproveRequest.getArticleId());
        prePopulateEditRequestForApproval(editRequestHandlerHelperBean, ArticleConst.ARTICLE_EDIT_REQUEST_APPROVED);
        populateEditRequestByArticleForApproval(editRequestHandlerHelperBean);
        populateEditRequestByUserForApproval(editRequestHandlerHelperBean);
        populateSharedArticleFromDb(editRequestHandlerHelperBean);
        articleDAOI.saveArticleApprovalRequest(editRequestHandlerHelperBean);
    }

    /***
     * Process of posting edit article draft request
     * 1. Populate edit request from db
     * 2. Load shared article bean from db
     * 3. Populate article edit draft request from db
     * 4. Save requests in db tables
     * @param articleEditDraftRequest
     * @return ArticleEditDraftResponse
     */
    @Override
    public void postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean =  populateEditRequestHelperBeanFromDb(articleEditDraftRequest.getEditRequestId(), articleEditDraftRequest.getArticleId());
        populateSharedArticleFromDb(editRequestHandlerHelperBean);
        populateEditArticleDraft(articleEditDraftRequest, editRequestHandlerHelperBean);
        articleDAOI.saveArticleEditDraftRequest(editRequestHandlerHelperBean);
    }

    /***
     * This will add interest topic to db
     * @param topicAddRequest
     * @return topicAddResponse
     */
    @Override
    public TopicAddResponse addTopic(TopicAddRequest topicAddRequest){
        TopicAddResponse topicAddResponse = new TopicAddResponse();
        articleDAOI.saveTopic(topicAddRequest.getTopicName());
        topicAddResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        topicAddResponse.setNarration(ArticleConst.ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION);
        return topicAddResponse;
    }

    /***
     * Load all available topics from db and send
     * @return TopicResponse
     */
    @Override
    public TopicResponse findAllTopic(){
        TopicResponse topicResponse = new TopicResponse();
        Optional<List<Topics>> topicsList = articleDAOI.loadAllTopicsEntries();
        topicsList.ifPresent(topics -> topicResponse.setTopicName(topics));
        return topicResponse;
    }

    //---------------------------********* Article handler provide methods *********---------------------------//

    protected void prePopulatedForNewArticleFlow(ArticleBean articleBean){
        articleBean.setArticleId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        articleBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void prePopulateForUpdateArticleFlow(ArticleBean articleBean, ArticleHandlerHelperBean articleHandlerHelperBean){
        Optional<Article> currentArticleResponse = articleDAOI.getArticleById(articleBean.getArticleId());
        var currentArticle = currentArticleResponse
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_NOT_FOUND_ERROR + articleBean.getArticleId()));
        updateArticleHistoryToPushIntoHistory(currentArticle, articleHandlerHelperBean); //Update article history bean for persistence
        articleBean.setCreatedDate(currentArticle.getCreatedDate());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void populateUpdateArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleBean articleBean){
        Article article = new Article(articleBean);//Create article bean
        articleHandlerHelperBean.setUpdatedArticle(article);
    }

    protected void populateUserAssignArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleBean articleBean){
        ArticleByUser articleByUser = new ArticleByUser(articleBean);
        articleHandlerHelperBean.setArticleByUser(articleByUser);
    }

    protected void populateDeleteArticle(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleDeleteRequest.getArticleId());
        currentArticleInDb.ifPresent((article) -> articleHandlerHelperBean.setUpdatedArticle(article));
    }

    protected void populateDeleteArticleByUser(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<ArticleByUser> currentArticleByUserInDb = articleDAOI.getArticleByUserId(articleDeleteRequest.getUserName());
        currentArticleByUserInDb.ifPresent((articleByUser) -> articleHandlerHelperBean.setArticleByUser(articleByUser));
    }

    protected void populateDeleteArticleHistory(ArticleHandlerHelperBean articleHandlerHelperBean, ArticleDeleteRequest articleDeleteRequest){
//        Optional<ArticleHistory> currentArticleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(articleDeleteRequest.getArticleId());
//        currentArticleHistoryInDb.ifPresent((articleHistory) -> articleHandlerHelperBean.setArticleHistory(articleHistory));
    }

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

    //Populate article details to send response back to browser
    protected void populateArticleDetailsFromDb(ArticleResponse articleResponse, String articleId){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleId);
        //Only load if article exists and active
        var currentArticle = currentArticleInDb
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_NOT_FOUND_ERROR + articleId));
        articleResponse.setArticle(currentArticle);
    }

    protected void updateArticleHistoryToPushIntoHistory(Article oldArticle, ArticleHandlerHelperBean articleHandlerHelperBean){
//        ArticleHistory updatedArticleHistory = new ArticleHistory();
//        updatedArticleHistory.setArticleId(oldArticle.getArticleId());
//        Optional<ArticleHistory> currentArticleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(oldArticle.getArticleId());
//        currentArticleHistoryInDb.ifPresent(articleHistory -> updatedArticleHistory.setOldArticles(articleHistory.getOldArticles()));
//        updatedArticleHistory.getOldArticles().add(oldArticle);
//        articleHandlerHelperBean.setArticleHistory(updatedArticleHistory);
    }

    protected EditRequest prePopulateArticleEditRequest(ArticleEditRequest articleEditRequest){
        EditRequest articleEditBean = articleEditRequest.getArticleEditRequest();
        String articleEditRequestId = keyGenerator.articleEditRequestGenerator(articleEditBean.getArticleId(), articleEditBean.getRequesterId());
        articleEditBean.setEditRequestId(articleEditRequestId); //Update article edit request id (~Generate)
        articleEditBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        articleEditBean.setModifiedDate(articleEditBean.getModifiedDate());
        articleEditBean.setEditRequestStatus(ArticleConst.ARTICLE_EDIT_REQUEST_PENDING); //Update article edit request status to pending
        return articleEditBean;
    }

    protected EditRequestHandlerHelperBean populateEditRequestHelperBean(EditRequest editRequest){
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = new EditRequestHandlerHelperBean();
        editRequestHandlerHelperBean.setEditRequest(editRequest);
        return editRequestHandlerHelperBean;
    }

    protected void populateEditRequestByArticle(EditRequestHandlerHelperBean editRequestHandlerHelperBean, EditRequest articleEditBean){
        EditRequestByArticle editRequestByArticle = new EditRequestByArticle(articleEditBean);
        editRequestHandlerHelperBean.setEditRequestByArticle(editRequestByArticle);
    }

    protected void populateEditRequestByUser(EditRequestHandlerHelperBean editRequestHandlerHelperBean, EditRequest articleEditBean){
        EditRequestByUser editRequestByUser = new EditRequestByUser(articleEditBean);
        editRequestHandlerHelperBean.setEditRequestByUser(editRequestByUser);
    }

    protected EditRequestHandlerHelperBean populateEditRequestHelperBeanFromDb(String editRequestId, String articleId)  {
        var currentEditRequest = articleDAOI.getArticleEditRequest(editRequestId, articleId)
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_EDIT_REQ_NOT_FOUND_ERROR + editRequestId));
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = new EditRequestHandlerHelperBean();
        editRequestHandlerHelperBean.setEditRequest(currentEditRequest);
        return editRequestHandlerHelperBean;
    }

    protected void prePopulateEditRequestForApproval(EditRequestHandlerHelperBean editRequestHandlerHelperBean, String status){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequest.setEditRequestStatus(status);
        editRequest.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void populateEditRequestByArticleForApproval(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        Optional<EditRequestByArticle> currentEditRequest = articleDAOI.getArticleEditRequestForArticleId(editRequest.getArticleId());
        currentEditRequest.ifPresent(editRequestByArticle -> {
            editRequestByArticle.setEditRequestStatus(editRequest.getEditRequestStatus());
            editRequestByArticle.setModifiedDate(editRequest.getModifiedDate());
            editRequestHandlerHelperBean.setEditRequestByArticle(editRequestByArticle);
        });
    }

    protected void populateEditRequestByUserForApproval(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        Optional<EditRequestByUser> currentEditRequestForUser = articleDAOI.getArticleEditRequestForUserId(editRequest.getOwnerId());
        currentEditRequestForUser.ifPresent(editRequestByUser -> {
            editRequestByUser.setEditRequestStatus(editRequest.getEditRequestStatus());
            editRequestByUser.setModifiedDate(editRequest.getModifiedDate());
            editRequestHandlerHelperBean.setEditRequestByUser(editRequestByUser);
        });
    }

    protected void populateSharedArticleFromDb(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        EditRequest editRequest = editRequestHandlerHelperBean.getEditRequest();
        editRequestHandlerHelperBean.setSharedArticles(new SharedArticles(editRequest));
        editRequestHandlerHelperBean.setSharedArticlesWithUser(new SharedArticlesWithUser(editRequest));
    }

    public void populateArticleAuthorDetails(ArticleResponse articleResponse) {
        Article article = articleResponse.getArticle();
        Optional<AuthorDetailResponse> authorDetailResponseBean = externalRestWriterI.findAuthorDetailsByLoginName(article.getUserId());
        var authorDetails = authorDetailResponseBean
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.AUTHOR_NOT_FOUND_ERROR + article.getUserId()));
        articleResponse.setAuthorDetails(authorDetails);
    }

    protected void populateEditArticleDraft(ArticleEditDraftRequest articleEditDraftRequest, EditRequestHandlerHelperBean editRequestHandlerHelperBean){
//        EditDraftArticles editDraftArticles = new EditDraftArticles();
//        editDraftArticles.setArticleId(articleEditDraftRequest.getArticleId());
//        Optional<EditDraftArticles> editDraftArticlesDb = articleDAOI.getEditDraftArticleByArticleId(articleEditDraftRequest.getArticleId());
//        editDraftArticlesDb.ifPresent(draftArticle -> {editDraftArticles.setDraftArticles(draftArticle.getDraftArticles());});
//        Article draftArticle = new Article(articleEditDraftRequest.getArticleBean());
//        editDraftArticles.getDraftArticles().put(articleEditDraftRequest.getEditRequestId(), draftArticle);
//        editRequestHandlerHelperBean.setEditDraftArticles(editDraftArticles);
    }


}

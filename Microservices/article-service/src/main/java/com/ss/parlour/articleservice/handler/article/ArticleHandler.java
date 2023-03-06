package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
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
    private CommentHandlerI commentHandlerI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private ExternalRestWriterI externalRestWriterI;

    /***
     * Handle when user request post create
     * 1. Check request has article id -> If not then it is new article create request
     * 2. If request has article id then article edit or update request -> Then keep histry and udate new request
     * @param articleBean
     * @return
     */
    @Override
    public Article processCreateArticleRequest(ArticleBean articleBean){
        ArticleUpdateHelperBean articleUpdateHelperBean = new ArticleUpdateHelperBean();
        if (articleBean.getId() == null || articleBean.getId().isEmpty()){ //New article request
            prePopulatedForNewArticleFlow(articleBean);
        } else { //Article update approve request flow
            prePopulateForUpdateArticleFlow(articleBean, articleUpdateHelperBean);
        }
        Article article = populateUpdatedArticle(articleBean);//Create article bean
        populateUpdateArticle(articleUpdateHelperBean, article);//Populate article bean
        populateUserAssignArticle(articleUpdateHelperBean, article);//Populate article by user bean
        //1. Update Article table
        //2. If exists update article history table
        //3. Update article by user table
        articleDAOI.saveArticleCreateRequest(articleUpdateHelperBean);
        return article;
    }

    /***
     * Update saved article object in db >> Update Article table
     * Handle when user add post like
     * @param likeBean
     */
    @Override
    public void addLikeRequest(LikeBean likeBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(likeBean.getArticleId());
        existingArticleBean.ifPresent((article) -> updateArticleVote(likeBean, article));
    }

    /***
     * 1. If delete request article exists --> Then update article status to inactive
     * 2. If required can be removed entry from list as well
     * Handle when user delete an article
     * @param articleDeleteRequest
     */
    @Override
    public void processDeleteArticleRequest(ArticleDeleteRequest articleDeleteRequest){
        ArticleUpdateHelperBean articleUpdateHelperBean = new ArticleUpdateHelperBean();
        //1. Delete article entry
        //2. Delete article entry by user
        //3. Delete article history entry
        populateDeleteArticle(articleUpdateHelperBean, articleDeleteRequest);
        populateDeleteArticleByUser(articleUpdateHelperBean, articleDeleteRequest);
        populateDeleteArticleHistory(articleUpdateHelperBean, articleDeleteRequest);
        articleDAOI.deleteArticleEntry(articleUpdateHelperBean);
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
        populateArticleDetails(articleResponse, articleId);
        populateArticleAuthorDetails(articleResponse);
        populateArticleComments(articleResponse, articleId);
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
        currentUserArticleFromDb.ifPresent(
                userArticle -> currentUserAssignArticle.set(new ArrayList<>(userArticle.getUserCreatedArticles().values()))
        );
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
        Optional<ArticleHistory> articleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequest.getArticleId());
        articleHistoryInDb.ifPresent((articleHistory -> articleHistoryResponse.setArticleHistoryList(articleHistory.getOldArticles())));
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
    public ArticleEditRequestResponse processArticleEditRequest(ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = new ArticleEditRequestResponse();
        EditRequest articleEditBean = articleEditRequest.getArticleEditRequest();
        String articleEditRequestId = keyGenerator.articleEditRequestGenerator(articleEditBean.getArticleId(), articleEditBean.getRequesterId());
        articleEditBean.setEditRequestId(articleEditRequestId); //Update article edit request id (~Generate)
        articleEditBean.setEditRequestStatus(ArticleConst.ARTICLE_EDIT_REQUEST_PENDING); //Update article edit request status to pending
        //Process article edit request (update article edit request + article edit request for user)
        EditRequestHelperBean editRequestHelperBean =  populateEditRequestHelperBean(articleEditBean);//Populate article edit request
        populateEditRequestByArticle(editRequestHelperBean);//Populate article edit request for article
        populateEditRequestByUser(editRequestHelperBean);//Populate article  edit request for user
        articleDAOI.saveArticleEditRequest(editRequestHelperBean);//Save entries into db
        articleEditRequestResponse.setEditRequestId(articleEditRequestId);
        articleEditRequestResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleEditRequestResponse.setNarration(ArticleConst.SUCCESSFULLY_PLACE_EDIT_REQUEST);
        return articleEditRequestResponse;
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
    public ArticleEditApproveResponse processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = new ArticleEditApproveResponse();
        EditRequestHelperBean editRequestHelperBean =  populateEditRequestHelperBeanFromDb(articleEditApproveRequest.getEditRequestId());
        updateEditRequestForApproval(editRequestHelperBean, ArticleConst.ARTICLE_EDIT_REQUEST_APPROVED);
        populateEditRequestByArticleForApproval(editRequestHelperBean);
        populateEditRequestByUserForApproval(editRequestHelperBean);
        populateSharedArticleFromDb(editRequestHelperBean, ArticleConst.ARTICLE_EDIT_REQUEST_APPROVED);
        articleDAOI.saveArticleApprovalRequest(editRequestHelperBean);
        articleEditApproveResponse.setEditRequestId(articleEditApproveRequest.getEditRequestId());
        articleEditApproveResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleEditApproveResponse.setNarration(ArticleConst.SUCCESSFULLY_APPROVED_EDIT_REQUEST);
        return articleEditApproveResponse;
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
    public ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditDraftResponse = new ArticleEditDraftResponse();
        EditRequestHelperBean editRequestHelperBean =  populateEditRequestHelperBeanFromDb(articleEditDraftRequest.getEditRequestId());
        populateSharedArticleFromDb(editRequestHelperBean, ArticleConst.ARTICLE_STATUS_EDIT_DRAFT_SUBMIT);
        populateEditArticleDraft(articleEditDraftRequest, editRequestHelperBean);
        articleDAOI.saveArticleEditDraftRequest(editRequestHelperBean);
        articleEditDraftResponse.setEditRequestId(articleEditDraftRequest.getEditRequestId());
        articleEditDraftResponse.setArticleId(articleEditDraftRequest.getArticleId());
        articleEditDraftResponse.setNarration(ArticleConst.ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION);
        return articleEditDraftResponse;
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
        articleBean.setId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        articleBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void prePopulateForUpdateArticleFlow(ArticleBean articleBean, ArticleUpdateHelperBean articleUpdateHelperBean){
        Optional<Article> currentArticleResponse = articleDAOI.getArticleById(articleBean.getId());
        var currentArticle = currentArticleResponse
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_NOT_FOUND_ERROR + articleBean.getId()));
        updateArticleHistoryToPushIntoHistory(currentArticle, articleUpdateHelperBean); //Update article history bean for persistence
        articleBean.setCreatedDate(currentArticle.getCreatedDate());
        articleBean.setModifiedDate(DateUtils.currentSqlTimestamp());
    }

    protected void populateUpdateArticle(ArticleUpdateHelperBean articleUpdateHelperBean, Article article){
        articleUpdateHelperBean.setUpdatedArticle(article);
    }

    protected void populateUserAssignArticle(ArticleUpdateHelperBean articleUpdateHelperBean, Article article){
        ArticleByUser articleByUser = new ArticleByUser();
        articleByUser.setUserId(articleByUser.getUserId());
        Optional<ArticleByUser> currentUserCreatedArticle = articleDAOI.getArticleByUserId(article.getUserName());
        currentUserCreatedArticle.ifPresent(userCreatedArticles -> articleByUser.setUserCreatedArticles(userCreatedArticles.getUserCreatedArticles()));
        articleByUser.getUserCreatedArticles().put(article.getId(), article);
        articleUpdateHelperBean.setArticleByUser(articleByUser);
    }

    protected void populateDeleteArticle(ArticleUpdateHelperBean articleUpdateHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleDeleteRequest.getArticleId());
        currentArticleInDb.ifPresent((article) -> articleUpdateHelperBean.setUpdatedArticle(article));
    }

    protected void populateDeleteArticleByUser(ArticleUpdateHelperBean articleUpdateHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<ArticleByUser> currentArticleByUserInDb = articleDAOI.getArticleByUserId(articleDeleteRequest.getUserName());
        currentArticleByUserInDb.ifPresent((articleByUser) -> articleUpdateHelperBean.setArticleByUser(articleByUser));
    }

    protected void populateDeleteArticleHistory(ArticleUpdateHelperBean articleUpdateHelperBean, ArticleDeleteRequest articleDeleteRequest){
        Optional<ArticleHistory> currentArticleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(articleDeleteRequest.getArticleId());
        currentArticleHistoryInDb.ifPresent((articleHistory) -> articleUpdateHelperBean.setArticleHistory(articleHistory));
    }

    protected void populateArticleComments(ArticleResponse articleResponse, String articleId){
        articleResponse.setArticleComments(commentHandlerI.getCommentListForPost(articleId));
    }

    protected Article returnArticle(Article article){
        return article;
    }

    //Update article like / unlike status
    protected void updateArticleVote(LikeBean likeBean, Article oldArticle){
        Set<String> likedUsers = oldArticle.getLikedList();
        Set<String> unLikeUsers = oldArticle.getUnLikedList();

        switch (likeBean.getStatus()){ //Update user like or unlike list based on user input
            case ArticleConst.USER_LIKED -> processUserLikedRequest(likeBean, likedUsers, unLikeUsers);
            case ArticleConst.USER_UNLIKED -> processUserUnLikedRequest(likeBean, likedUsers, unLikeUsers);
            default -> processUserNeutralLikedRequest(likeBean, likedUsers, unLikeUsers);
        }

        Article updatedArticle = oldArticle;
        updatedArticle.setLikedList(likedUsers);
        updatedArticle.setUnLikedList(unLikeUsers);
        articleDAOI.saveArticle(updatedArticle);
    }

    protected void processUserLikedRequest(LikeBean likeBean, Set<String> likedUsers, Set<String> unLikeUsers){
        likedUsers.add(likeBean.getUserName());
        unLikeUsers.removeIf(username -> username.equals(likeBean.getUserName()));
    }

    protected void processUserUnLikedRequest(LikeBean likeBean, Set<String> likedUsers, Set<String> unLikeUsers){
        unLikeUsers.add(likeBean.getUserName());
        likedUsers.removeIf(username -> username.equals(likeBean.getUserName()));
    }

    protected void processUserNeutralLikedRequest(LikeBean likeBean, Set<String> likedUsers, Set<String> unLikeUsers){
        likedUsers.removeIf(username -> username.equals(likeBean.getUserName()));
        unLikeUsers.removeIf(username -> username.equals(likeBean.getUserName()));
    }

    //Populate article details to send response back to browser
    protected void populateArticleDetails(ArticleResponse articleResponse, String articleId){
        Optional<Article> currentArticleInDb = articleDAOI.getArticleById(articleId);
        //Only load if article exists and active
        var currentArticle = currentArticleInDb
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_NOT_FOUND_ERROR + articleId));
        articleResponse.setArticle(currentArticle);
    }


    //Populate Article bean
    protected Article populateUpdatedArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setUserName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setStatus(articleBean.getStatus());
        article.setCategoryId(articleBean.getCategoryId());
        article.setThumbnailUrl(articleBean.getThumbnailUrl());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        return article;
    }


    protected void updateArticleHistoryToPushIntoHistory(Article oldArticle, ArticleUpdateHelperBean articleUpdateHelperBean){
        ArticleHistory updatedArticleHistory = new ArticleHistory();
        updatedArticleHistory.setArticleId(oldArticle.getId());
        Optional<ArticleHistory> currentArticleHistoryInDb = articleDAOI.getArticleHistoryByArticleId(oldArticle.getId());
        currentArticleHistoryInDb.ifPresent(articleHistory -> updatedArticleHistory.setOldArticles(articleHistory.getOldArticles()));
        updatedArticleHistory.getOldArticles().add(oldArticle);
        articleUpdateHelperBean.setArticleHistory(updatedArticleHistory);

    }

    protected EditRequestHelperBean populateEditRequestHelperBean(EditRequest editRequest){
        EditRequestHelperBean editRequestHelperBean = new EditRequestHelperBean();
        editRequestHelperBean.setEditRequest(editRequest);
        return editRequestHelperBean;
    }

    protected void populateEditRequestByArticle(EditRequestHelperBean editRequestHelperBean){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        EditRequestByArticle editRequestByArticleBean = new EditRequestByArticle();
        editRequestByArticleBean.setArticleId(editRequest.getArticleId());
        Optional<EditRequestByArticle> currentEditRequest = articleDAOI.getArticleEditRequestForArticleId(editRequest.getArticleId());
        currentEditRequest.ifPresent(editRequestByArticle -> {
            editRequestByArticleBean.setEditRequestByArticleMap(editRequestByArticle.getEditRequestByArticleMap());
        });
        editRequestByArticleBean.getEditRequestByArticleMap().put(editRequest.getEditRequestId(), editRequest);
        editRequestHelperBean.setEditRequestByArticle(editRequestByArticleBean);
    }

    protected void populateEditRequestByUser(EditRequestHelperBean editRequestHelperBean){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        EditRequestByUser articleEditRequestBeanForUser = new EditRequestByUser();
        articleEditRequestBeanForUser.setUserId(editRequest.getOwnerId());
        Optional<EditRequestByUser> currentEditRequest = articleDAOI.getArticleEditRequestForUserId(editRequest.getOwnerId());
        currentEditRequest.ifPresent(editRequestByUser -> {
            articleEditRequestBeanForUser.setEditRequestByUserMap(editRequestByUser.getEditRequestByUserMap());
        });
        articleEditRequestBeanForUser.getEditRequestByUserMap().put(editRequest.getEditRequestId(), editRequest);
        editRequestHelperBean.setEditRequestByUser(articleEditRequestBeanForUser);
    }

    protected EditRequestHelperBean populateEditRequestHelperBeanFromDb(String editRequestId)  {
        var currentEditRequest = articleDAOI.getArticleEditRequest(editRequestId)
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.ARTICLE_EDIT_REQ_NOT_FOUND_ERROR + editRequestId));
        EditRequestHelperBean editRequestHelperBean = new EditRequestHelperBean();
        editRequestHelperBean.setEditRequest(currentEditRequest);
        return editRequestHelperBean;
    }

    protected void updateEditRequestForApproval(EditRequestHelperBean editRequestHelperBean, String status){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        editRequest.setEditRequestStatus(status);
    }

    protected void populateEditRequestByArticleForApproval(EditRequestHelperBean editRequestHelperBean){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        Optional<EditRequestByArticle> currentEditRequest = articleDAOI.getArticleEditRequestForArticleId(editRequest.getArticleId());
        currentEditRequest.ifPresent(editRequestByArticle -> {
            processEditRequestByArticleForApproval(editRequestHelperBean, editRequestByArticle);
        });
    }

    protected void populateEditRequestByUserForApproval(EditRequestHelperBean editRequestHelperBean){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        Optional<EditRequestByUser> currentEditRequestForUser = articleDAOI.getArticleEditRequestForUserId(editRequest.getOwnerId());
        currentEditRequestForUser.ifPresent(editRequestByUser -> {
            processEditRequestByUserForApproval(editRequestHelperBean, editRequestByUser);
        });
    }

    protected void processEditRequestByArticleForApproval(EditRequestHelperBean editRequestHelperBean, EditRequestByArticle editRequestByArticle){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        HashMap<String, EditRequest> articleEditBeanMap = editRequestByArticle.getEditRequestByArticleMap();
        articleEditBeanMap.put(editRequest.getEditRequestId(), editRequest);
        editRequestByArticle.setEditRequestByArticleMap(articleEditBeanMap);
        editRequestHelperBean.setEditRequestByArticle(editRequestByArticle);
    }

    protected void processEditRequestByUserForApproval(EditRequestHelperBean editRequestHelperBean, EditRequestByUser editRequestByUser){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        HashMap<String, EditRequest> articleEditBeanMap = editRequestByUser.getEditRequestByUserMap();
        articleEditBeanMap.put(editRequest.getEditRequestId(), editRequest);
        editRequestByUser.setEditRequestByUserMap(articleEditBeanMap);
        editRequestHelperBean.setEditRequestByUser(editRequestByUser);
    }

    protected void populateSharedArticleFromDb(EditRequestHelperBean editRequestHelperBean, String status){
        EditRequest editRequest = editRequestHelperBean.getEditRequest();
        SharedArticles sharedArticles = new SharedArticles();
        sharedArticles.setUserId(editRequest.getOwnerId());
        Optional<SharedArticles> shareArticleInDb = articleDAOI.getSharedArticlesForUserId(editRequest.getOwnerId());
        shareArticleInDb.ifPresent(currentSharedArticles -> {
            sharedArticles.setSharedArticleBeanMap(currentSharedArticles.getSharedArticleBeanMap());
        });
        SharedArticleBean sharedArticleBean = populateShareArticleBean(editRequest, status);
        sharedArticles.getSharedArticleBeanMap().put(editRequest.getEditRequestId(), sharedArticleBean);
        articleDAOI.saveSharedArticles(sharedArticles);
    }

    protected SharedArticleBean populateShareArticleBean(EditRequest editRequest, String status){
        SharedArticleBean sharedArticleBean = new SharedArticleBean();
        sharedArticleBean.setArticleId(editRequest.getArticleId());
        sharedArticleBean.setStatus(status);
        sharedArticleBean.setAuthor(editRequest.getOwnerId());
        return sharedArticleBean;
    }

    public void populateArticleAuthorDetails(ArticleResponse articleResponse) {
        Article article = articleResponse.getArticle();
        Optional<AuthorDetailResponse> authorDetailResponseBean = externalRestWriterI.findAuthorDetailsByLoginName(article.getUserName());
        var authorDetails = authorDetailResponseBean
                .orElseThrow(() -> new ArticleServiceRuntimeException(ArticleErrorCodes.AUTHOR_NOT_FOUND_ERROR + article.getUserName()));
        articleResponse.setAuthorDetails(authorDetails);
    }

    protected void populateEditArticleDraft(ArticleEditDraftRequest articleEditDraftRequest, EditRequestHelperBean editRequestHelperBean){
        EditDraftArticles editDraftArticles = new EditDraftArticles();
        editDraftArticles.setArticleId(articleEditDraftRequest.getArticleId());
        Optional<EditDraftArticles> editDraftArticlesDb = articleDAOI.getEditDraftArticleByArticleId(articleEditDraftRequest.getArticleId());
        editDraftArticlesDb.ifPresent(draftArticle -> {editDraftArticles.setDraftArticles(draftArticle.getDraftArticles());});
        Article draftArticle = populateUpdatedArticle(articleEditDraftRequest.getArticleBean());
        editDraftArticles.getDraftArticles().put(articleEditDraftRequest.getEditRequestId(), draftArticle);
        editRequestHelperBean.setEditDraftArticles(editDraftArticles);
    }


}

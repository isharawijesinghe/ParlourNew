package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import com.ss.parlour.articleservice.writer.ExternalRestWriterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
     * @param articleBean
     * @return
     */
    @Override
    public Article processCreateArticleRequest(ArticleBean articleBean){
        if (articleBean.getId() == null || articleBean.getId().isEmpty()){ //New article request
            articleBean.setId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        } else { //Article update approve request flow
            Optional<Article> currentArticle = articleDAOI.getArticleById(articleBean.getId());
            currentArticle.ifPresent(article -> updateArticle(article)); //Update ArticleHistory table
        }
        Article article = populateArticle(articleBean); //Populate article bean
        updateArticle(article); //Create or update article >> Update Article table
        return article;
    }

    /***
     * Handle when user add post like
     * @param likeBean
     */
    @Override
    public void handleLikeRequest(LikeBean likeBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(likeBean.getArticleId());
        //Update saved article object in db >> Update Article table
        existingArticleBean.ifPresent((article) -> updateArticleVote(likeBean, article));
    }

    /***
     * Handle when user delete an article
     * @param articleDeleteRequest
     */
    @Override
    public void processDeleteArticleRequest(ArticleDeleteRequest articleDeleteRequest){
        Optional<Article> exitingArticle = articleDAOI.getArticleById(articleDeleteRequest.getArticleId());
        //If delete request article exists --> Then update article status to inactive
        //If required this can be deleted
        exitingArticle.ifPresent((article) -> processUpdateArticleStatus(article, ArticleConst.ARTICLE_INACTIVE));
    }

    /***
     * When user request to find article by id
     * @param articleRequest
     * @return
     */
    @Override
    public ArticleResponse findArticleById(ArticleRequest articleRequest){
        ArticleResponse articleResponse = new ArticleResponse();
        populateArticleDetails(articleRequest, articleResponse);
        populateArticleAuthorDetails(articleResponse);
        articleResponse.setArticleComments(commentHandlerI.getCommentListForPost(articleRequest));
        return articleResponse;
    }

    /***
     * When user request to find article history by id
     * @param articleHistoryRequest
     * @return
     */
    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = new ArticleHistoryResponse();
        Optional<ArticleHistory> currentArticleHistory = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequest.getArticleId());
        currentArticleHistory.ifPresent((articleHistory -> articleHistoryResponse.setArticleHistoryList(articleHistory.getOldArticles())));
        return articleHistoryResponse;
    }

    /***
     * Find user article by id
     * @param articleId
     * @return
     */
    @Override
    public Article findArticleDetailsById(String articleId){
        Optional<Article> currentArticle = articleDAOI.getArticleById(articleId);
        currentArticle.ifPresent(article -> returnArticle(article));
        return null;
    }

    /***
     * Process article edit request
     * 1. Populate edit request
     * 2. Populate edit request by article
     * 3. Populate edit request by user
     * 4. Save request into tables
     * @param articleEditRequest
     * @return
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

    //---------------------------********* Article handler provide methods *********---------------------------//

    protected Article returnArticle(Article article){
        return article;
    }

    //Update article status
    protected void processUpdateArticleStatus(Article currentArticle, int status){
        currentArticle.setStatus(status);
        articleDAOI.saveArticle(currentArticle);
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
    protected void populateArticleDetails(ArticleRequest articleRequest, ArticleResponse articleResponse){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(articleRequest.getArticleId());
        //Only load if article exists and active
        if (existingArticleBean.isPresent() && existingArticleBean.get().getStatus() == ArticleConst.ARTICLE_ACTIVE){
            Article article = existingArticleBean.get();
            articleResponse.setArticle(article);
        }
    }

    //Update article >> Update or save
    protected void updateArticle(Article article){
        articleDAOI.saveArticle(article);
    }

    //Populate Article bean
    protected Article populateArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setUserName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        article.setStatus(articleBean.getStatus());
        article.setCategoryId(articleBean.getCategoryId());
        return article;
    }

    //Update article history into db
    protected void updateArticleHistory(Article oldArticle){
        Optional<ArticleHistory> existingArticleHistoryBean = articleDAOI.getArticleHistoryByArticleId(oldArticle.getId());
        List<Article> articleHistoriesList = new ArrayList<>();
        if (existingArticleHistoryBean.isPresent()){
            articleHistoriesList = existingArticleHistoryBean.get().getOldArticles();
        }
        articleHistoriesList.add(oldArticle);
        ArticleHistory articleHistory = new ArticleHistory();
        articleHistory.setArticleId(existingArticleHistoryBean.get().getArticleId());
        articleHistory.setOldArticles(articleHistoriesList);
        articleDAOI.updateArticleHistory(articleHistory);
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
        Article draftArticle = populateArticle(articleEditDraftRequest.getArticleBean());
        editDraftArticles.getDraftArticles().put(articleEditDraftRequest.getEditRequestId(), draftArticle);
        editRequestHelperBean.setEditDraftArticles(editDraftArticles);
    }


}

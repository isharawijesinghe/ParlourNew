package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.dao.cassandra.CommentDAOI;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerFactoryI;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.handler.like.LikeHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.common.ArticleMsgHeader;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.validator.ArticleValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private ArticleHandlerI articleHandlerI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Autowired
    private ArticleValidatorI articleValidatorI;

    @Autowired
    private LikeHandlerI likeHandlerI;

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private CommentDAOI commentDAOI;

    @Autowired
    private LikeTypeHandlerFactoryI likeHandlerFactoryI;

    @Override
    public ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest){
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequest);
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        if (articleBean.getArticleId() == null || articleBean.getArticleId().isEmpty()){ //New article request
            articleHandlerI.prePopulatedForNewArticleFlow(articleBean);
        } else { //Article update approve request flow
            articleHandlerI.prePopulateForUpdateArticleFlow(articleBean);
        }
        //1. Populate new article bean
        //2. Populate new article user assign bean
        ArticleHandlerHelperBean articleHandlerHelperBean = articleHandlerI.populateArticleCreateHandlerHelperBean(articleBean);
        //1. Update Article table
        //2. Update article by user table
        articleDAOI.saveArticleCreateRequest(articleHandlerHelperBean);
        articleCommonResponse.setArticleId(articleBean.getArticleId());
        return  ArticleResponse.builder().body(articleCommonResponse).articleMsgHeader(articleCreateRequest.getArticleMsgHeader())
                .httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).message(ArticleConst.SUCCESSFULLY_CREATED_ARTICLE).build();
    }

    @Override
    public ArticleResponse addComment(CommentCreateRequest commentCreateRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        commentHandlerI.prePopulateCommentId(commentBean); //Generate comment id if not exists
        commentHandlerI.prePopulateCommentCreatedDate(commentBean); //Add comment created date if not exists
        //1. Create new comment bean
        //2. Create new comment group
        CommentAddHelperBean commentAddHelperBean = commentHandlerI.populateCommentAddHelperBean(commentBean);
        //1. Save new comment bean
        //2. Save new comment group
        commentDAOI.saveComment(commentAddHelperBean);
        commentCommonResponse.setCommentId(commentBean.getCommentId());
        return  ArticleResponse.builder().body(commentCommonResponse).articleMsgHeader(commentCreateRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_COMMENT_ADDED).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse addLike(LikeRequest likeRequest){
        LikeRequestBean likeRequestBean = articleValidatorI.validateAddLikeRequest(likeRequest); //Doing basic initial validations
        likeHandlerFactoryI.getLikeTypeHandlerI(likeRequestBean.getLikeType()).addLikeRequest(likeRequestBean);
        return  ArticleResponse.builder().articleMsgHeader(likeRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LIKE_ADDED).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findArticleById(String articleId){
        ArticleDetailsResponse articleDetailsResponse = new ArticleDetailsResponse();
        //1. Load and populate article details
        //2. Load and populate article author details
        //3. Load and populate article contributors
        articleHandlerI.populateArticleDetailsFromDb(articleDetailsResponse, articleId);
        articleHandlerI.populateArticleAuthorDetails(articleDetailsResponse);
        articleHandlerI.populateArticleContributorsDetails(articleDetailsResponse, articleId);
        return  ArticleResponse.builder().body(articleDetailsResponse).articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LOAD_ARTICLE_DETAILS).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findArticleByUser(ArticleListRequest articleListRequest){
        //Load article posted by user from db
        ArticleListResponse articleListResponse = articleHandlerI.findArticleByUser(articleListRequest);
        return  ArticleResponse.builder().body(articleListResponse).articleMsgHeader(articleListRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LIKE_ADDED).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = articleHandlerI.findArticleHistoryById(articleHistoryRequest);
        return  ArticleResponse.builder().body(articleHistoryResponse).articleMsgHeader(articleHistoryRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_HISTORY_SEARCH_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        //1. Load article entry from db
        //2. Load article entry by user from db
        //3. Load article history entry from db
        ArticleHandlerHelperBean articleHandlerHelperBean = articleHandlerI.populateArticleDeleteHelperBean(articleDeleteRequest);
        //1. Delete article entry
        //2. Delete article entry by user
        //3. Delete article history entry
        articleDAOI.deleteArticleEntry(articleHandlerHelperBean);
        articleCommonResponse.setArticleId(articleDeleteRequest.getArticleDeleteRequestInner().getArticleId());
        return  ArticleResponse.builder().body(articleCommonResponse).articleMsgHeader(articleDeleteRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequest); //Doing basic initial validations
        //1. Load comment bean from db
        //2. Load comment group from db
        CommentDeleteHelperBean commentDeleteHelperBean = commentHandlerI.populateCommentDeleteHelperBean(commentDeleteRequest);
        //1. Delete all current comment
        //2. Delete all child comments
        commentDAOI.deleteComment(commentDeleteHelperBean);
        return  ArticleResponse.builder().articleMsgHeader(commentDeleteRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_COMMENT_DELETED).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findArticleComments(CommentRequest commentRequest){
        CommentResponse commentResponse = commentHandlerI.findArticleComments(commentRequest);
        return  ArticleResponse.builder().body(commentResponse).articleMsgHeader(commentRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse articleEditRequest(ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = new ArticleEditRequestResponse();
        //1. Create article new edit request
        //2. Create article new edit request by article
        //3. Create article new edit request by user
        EditRequestHandlerHelperBean articleEditRequestHelperBean = articleHandlerI.populateArticleEditRequestHelperBean(articleEditRequest);
        //1. Save article new edit request
        //2. Save article new edit request by article
        //3. Save article new edit request by user
        articleDAOI.saveArticleEditRequest(articleEditRequestHelperBean);//Save entries into db
        articleEditRequestResponse.setEditRequestId(articleEditRequestHelperBean.getEditRequest().getEditRequestId());
        return  ArticleResponse.builder().body(articleEditRequestResponse).articleMsgHeader(articleEditRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_PLACE_EDIT_REQUEST).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = new ArticleEditApproveResponse();
        //1. Update edit request --> Changing status
        //2. Populate edit request by article after approval
        //3. Populate edit request by user after approval
        //4. Populate shared article data bean
        //5. Populate shared article with user data bean
        EditRequestHandlerHelperBean articleEditRequestApprovalHelperBean = articleHandlerI.populateArticleEditRequestApprovalHelperBean(articleEditApproveRequest);
        //1. Update edit request bean into db
        //2. Update edit request by article bean into db
        //3. Update edit request by user bean into db
        //4. Save shared article data bean into db
        //5. Update shared article with user data bean into db
        articleDAOI.saveArticleApprovalRequest(articleEditRequestApprovalHelperBean);
        articleEditApproveResponse.setEditRequestId(articleEditApproveRequest.getArticleEditApproveRequestInner().getEditRequestId());
        return  ArticleResponse.builder().body(articleEditApproveResponse).articleMsgHeader(articleEditApproveRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_APPROVED_EDIT_REQUEST).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findShareArticlesByUser(ArticleEditShareUserRequest articleEditShareUserRequest){
        ArticleEditShareUserResponse articleEditShareUserResponse = articleHandlerI.findShareArticlesByUser(articleEditShareUserRequest);
        return  ArticleResponse.builder().body(articleEditShareUserResponse).articleMsgHeader(articleEditShareUserRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LOADED_EDIT_APPROVE_REQUESTS).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditDraftResponse = new ArticleEditDraftResponse();
        //1. Load article edit request from db
        //2. Populate shared article data bean
        //3. Populate shared article with user data bean
        //4. Populate article edit draft data
        EditRequestHandlerHelperBean editRequestHandlerHelperBean = articleHandlerI.populateSaveArticleEditDraftRequestHelperBean(articleEditDraftRequest);
        //   Save article edit requests draft beans into db
        //1. Save shared articles
        //2. Save shared articles with users
        //3. Save article edit draft
        articleDAOI.saveArticleEditDraftRequest(editRequestHandlerHelperBean);
        articleEditDraftResponse.setEditRequestId(articleEditDraftRequest.getArticleEditDraftRequestInner().getEditRequestId());
        articleEditDraftResponse.setArticleId(articleEditDraftRequest.getArticleEditDraftRequestInner().getArticleId());
        return  ArticleResponse.builder().body(articleEditDraftResponse).articleMsgHeader(articleEditDraftRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse publishArticleEditDraft(ArticlePublishEditDraftRequest articlePublishEditDraftRequest){
        ArticlePublishEditDraftResponse articlePublishEditDraftResponse = new ArticlePublishEditDraftResponse();

        //Load article edit request from db
        //Load current article from db
        //Load saved edit article draft from db
        //1. Populate shared article data bean
        //2. Populate shared article with user data bean
        //3.Update old article with modified values
        //4.Populate article by user values
        //5.Populate article contributors
        ArticleEditPublishHelperBean articleEditPublishHelperBean = articleHandlerI.populatePublishArticleEditDraftRequestHelperBean(articlePublishEditDraftRequest);

        //1.Save modified article
        //2.Save shared article data bean
        //3.Save shared article with user data bean
        //4.Save article contributors
        articleDAOI.saveArticleEditPublishRequest(articleEditPublishHelperBean);
        articlePublishEditDraftResponse.setEditRequestId(articlePublishEditDraftResponse.getEditRequestId());
        return  ArticleResponse.builder().body(articlePublishEditDraftResponse).articleMsgHeader(articlePublishEditDraftRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse addTopic(TopicAddRequest topicAddRequest){
        articleDAOI.saveTopic(topicAddRequest.getTopicAddRequestInner().getTopicName());
        return  ArticleResponse.builder().articleMsgHeader(topicAddRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

    @Override
    public ArticleResponse findAllTopic(){
        TopicResponse topicResponse = articleHandlerI.findAllTopic();
        return  ArticleResponse.builder().body(topicResponse).articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION).httpStatus(200).zonedDateTime(ZonedDateTime.now(ZoneId.of("Z"))).build();
    }

}

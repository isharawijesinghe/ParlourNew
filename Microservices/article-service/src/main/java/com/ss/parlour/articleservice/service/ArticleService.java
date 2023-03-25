package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.handler.like.LikeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.validator.ArticleValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ArticleCommonResponse createArticle(ArticleCreateRequest articleCreateRequest){
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequest);
        String articleId = articleHandlerI.processCreateArticleRequest(articleBean);
        articleCommonResponse.setArticleId(articleId);
        articleCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_CREATED_ARTICLE);
        return articleCommonResponse;
    }

    @Override
    public CommentCommonResponse addComment(CommentCreateRequest commentCreateRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        String commentId = commentHandlerI.processAddCommentRequest(commentBean);
        commentCommonResponse.setCommentId(commentId);
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_ADDED);
        return commentCommonResponse;
    }

    @Override
    public LikeCommonResponse addLike(LikeRequest likeRequest){
        LikeRequestBean likeRequestBean = articleValidatorI.validateAddLikeRequest(likeRequest); //Doing basic initial validations
        return likeHandlerI.processAddLikeRequest(likeRequestBean);
    }

    @Override
    public ArticleResponse findArticleById(String articleId){
        return articleHandlerI.findArticleById(articleId);
    }

    @Override
    public ArticleListResponse findArticleByUser(ArticleListRequest articleListRequest){
        ArticleListResponse  articleListResponse = new ArticleListResponse();
        List<Article> currentUserArticleList = articleHandlerI.findArticleByUser(articleListRequest);
        articleListResponse.setArticleResponseList(currentUserArticleList);
        return articleListResponse;
    }

    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        return articleHandlerI.findArticleHistoryById(articleHistoryRequest);
    }

    @Override
    public ArticleCommonResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        articleHandlerI.processDeleteArticleRequest(articleDeleteRequest);
        articleCommonResponse.setArticleId(articleDeleteRequest.getArticleId());
        articleCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED);
        return articleCommonResponse;
    }

    @Override
    public CommentCommonResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequest); //Doing basic initial validations
        commentHandlerI.processDeleteCommentRequest(commentDeleteRequest);
        commentCommonResponse.setCommentId(commentDeleteRequest.getCommentId());
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_DELETED);
        return commentCommonResponse;
    }

    @Override
    public CommentResponse findArticleComments(CommentRequest commentRequest){
        CommentResponse commentResponse = new CommentResponse();
        List<CommentBean> commentBeanList = commentHandlerI.findArticleComments(commentRequest);
        commentResponse.setArticleComments(commentBeanList);
        commentResponse.setParentCommentId(commentRequest.getParentCommentId());
        commentResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentResponse.setNarration(ArticleConst.ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION);
        return commentResponse;
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        return articleHandlerI.findArticleDetailsById(articleId);
    }

    @Override
    public ArticleEditRequestResponse articleEditRequest(ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = new ArticleEditRequestResponse();
        String articleEditRequestId = articleHandlerI.processArticleEditRequest(articleEditRequest);
        articleEditRequestResponse.setEditRequestId(articleEditRequestId);
        articleEditRequestResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleEditRequestResponse.setNarration(ArticleConst.SUCCESSFULLY_PLACE_EDIT_REQUEST);
        return articleEditRequestResponse;
    }

    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = new ArticleEditApproveResponse();
        articleHandlerI.processArticleEditRequestApproval(articleEditApproveRequest);
        articleEditApproveResponse.setEditRequestId(articleEditApproveRequest.getEditRequestId());
        articleEditApproveResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleEditApproveResponse.setNarration(ArticleConst.SUCCESSFULLY_APPROVED_EDIT_REQUEST);
        return articleEditApproveResponse;
    }

    @Override
    public ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditDraftResponse = new ArticleEditDraftResponse();
        articleHandlerI.postArticleEditDraft(articleEditDraftRequest);
        articleEditDraftResponse.setEditRequestId(articleEditDraftRequest.getEditRequestId());
        articleEditDraftResponse.setArticleId(articleEditDraftRequest.getArticleId());
        articleEditDraftResponse.setNarration(ArticleConst.ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION);
        return articleEditDraftResponse;
    }

    @Override
    public TopicAddResponse addTopic(TopicAddRequest topicAddRequest){
        return articleHandlerI.addTopic(topicAddRequest);
    }

    @Override
    public TopicResponse findAllTopic(){
        return articleHandlerI.findAllTopic();
    }

}

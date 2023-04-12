package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.dao.cassandra.CommentDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequest;
import com.ss.parlour.articleservice.utils.bean.response.CommentCommonResponse;
import com.ss.parlour.articleservice.utils.bean.response.CommentResponse;
import com.ss.parlour.articleservice.utils.common.DateUtils;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommentHandler implements CommentHandlerI, LikeTypeHandlerI {

    @Autowired
    private CommentDAOI commentDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public void prePopulateCommentId(CommentBean commentBean){
        if(commentBean.getCommentId() == null || commentBean.getCommentId().isEmpty()){
            commentBean.setCommentId(keyGenerator.commentKeyGenerator(commentBean.getUserId(), commentBean.getCommentId()));
        }
    }

    @Override
    public void prePopulateCommentCreatedDate(CommentBean commentBean){
        Optional<Comment> currentComment = commentDAOI.getCommentByIdAndArticleId(commentBean.getCommentId(), commentBean.getCommentId());
        currentComment.ifPresentOrElse(comment -> commentBean.setCreatedDate(comment.getCreatedDate()),
                () -> commentBean.setCreatedDate(DateUtils.currentSqlTimestamp()));
    }

    @Override
    public CommentAddHelperBean populateCommentAddHelperBean(CommentBean commentBean){
        CommentAddHelperBean commentAddHelperBean = new CommentAddHelperBean();
        populateCommentBean(commentAddHelperBean, commentBean);
        populateCommentGroup(commentAddHelperBean, commentBean);
        return commentAddHelperBean;
    }

    @Override
    public CommentDeleteHelperBean populateCommentDeleteHelperBean(CommentDeleteRequest commentDeleteRequest){
        CommentDeleteRequest.CommentDeleteRequestInner commentDeleteRequestInner = commentDeleteRequest.getCommentDeleteRequestInner();
        CommentDeleteHelperBean commentDeleteHelperBean = new CommentDeleteHelperBean();
        populateCommentDeleteBeanFromDb(commentDeleteHelperBean, commentDeleteRequestInner.getArticleId(), commentDeleteRequestInner.getCommentId());
        populateCommentGroupDeleteBeanFromDb(commentDeleteHelperBean, commentDeleteRequestInner.getArticleId(), commentDeleteRequestInner.getCommentId());
        return commentDeleteHelperBean;
    }

    @Override
    public CommentResponse findArticleComments(CommentRequest commentRequest){
        CommentRequest.CommentRequestInner commentRequestInner = commentRequest.getCommentRequestInner();
        CommentResponse commentResponse = new CommentResponse();
        List<CommentBean> commentList = new ArrayList<>();
        Optional<List<CommentGroup>> currentCommentGroupList = commentDAOI.getCommentGroup(commentRequestInner.getParentCommentId(), commentRequestInner.getArticleId());
        currentCommentGroupList.ifPresent(commentGroupList -> commentGroupList.forEach(listItem -> commentList.add(new CommentBean(listItem)))); //Populate top level comment items
        populateChildComments(commentList); //Populate sub child comment lists
        commentResponse.setArticleComments(commentList);
        return commentResponse;
    }

    protected void populateCommentGroupDeleteBeanFromDb(CommentDeleteHelperBean commentDeleteHelperBean, String articleId, String commentId){
        List<CommentGroup> commentGroupList = populateCommentGroupFromDb(commentId, articleId);
        commentDeleteHelperBean.setCommentGroup(commentGroupList);
    }

    protected void populateCommentDeleteBeanFromDb(CommentDeleteHelperBean commentDeleteHelperBean, String articleId, String commentId){
        Comment comment = populateCommentFromDb(commentId, articleId);
        commentDeleteHelperBean.setComment(comment);
    }

    protected Comment populateCommentFromDb(String commentId, String articleId){
        Optional<Comment> currentComment = commentDAOI.getCommentByIdAndArticleId(commentId, articleId);
        var commentInDb = currentComment.orElseThrow(
                () -> new ArticleServiceRuntimeException(String.format(ArticleErrorCodes.COMMENT_NOT_FOUND_ERROR, commentId, articleId)));
        return commentInDb;
    }

    protected List<CommentGroup> populateCommentGroupFromDb(String commentId, String articleId){
        Optional<List<CommentGroup>> currentCommentGroupList = commentDAOI.getCommentGroup(articleId, commentId);;
        var commentGroupInDb = currentCommentGroupList.orElseThrow(
                () -> new ArticleServiceRuntimeException(String.format(ArticleErrorCodes.COMMENT_GROUP_NOT_FOUND_ERROR, commentId, articleId)));
        return commentGroupInDb;
    }

    @Override
    public void addLikeRequest(LikeRequestBean likeRequestBean){
        CommentLikeHandlerHelperBean commentLikeHandlerHelperBean = new CommentLikeHandlerHelperBean();
        likeRequestBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        populateCurrentUserCommentLikeStatus(commentLikeHandlerHelperBean, likeRequestBean);
        populateNewCommentUserLikeDate(commentLikeHandlerHelperBean, likeRequestBean);
        commentDAOI.updateArticleUserLikeRequest(commentLikeHandlerHelperBean);
    }

    protected void populateChildComments(List<CommentBean> commentList){
        commentList.forEach(commentBean -> loadAndAssignChildComments(commentBean));
    }

    protected void loadAndAssignChildComments(CommentBean commentBean){
        List<CommentBean> commentChildList = new ArrayList<>();
        commentBean.setSubCommentBean(commentChildList);
        Optional<List<CommentGroup>> currentCommentGroupList = commentDAOI.getCommentGroup(commentBean.getArticleId(), commentBean.getCommentId());
        currentCommentGroupList.ifPresent(commentGroupList -> commentGroupList.forEach(listItem -> commentBean.getSubCommentBean().add(new CommentBean(listItem))));
        if (!commentChildList.isEmpty()){
            commentChildList.forEach(childCommentBean -> loadAndAssignChildComments(childCommentBean));
        }
    }

    protected void populateCommentBean(CommentAddHelperBean commentAddHelperBean, CommentBean commentBean){
        Comment comment = new Comment(commentBean);
        commentAddHelperBean.setComment(comment);
    }

    protected void populateCommentGroup(CommentAddHelperBean commentAddHelperBean, CommentBean commentBean){
        CommentGroup commentGroup = new CommentGroup(commentBean);
        commentAddHelperBean.setCommentGroup(commentGroup);
    }


    protected void populateCurrentUserCommentLikeStatus(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean, LikeRequestBean likeRequestBean){
        Optional<LikeByComment> likeByComment = commentDAOI.getLikeByCommentEntry(likeRequestBean.getCommentId(), likeRequestBean.getArticleId(), likeRequestBean.getUserId());
        likeByComment.ifPresent(lbc -> {
            commentLikeHandlerHelperBean.setCurrentLikeByComment(lbc);
            Optional<LikeByCommentGroup> likeByCommentGroup = commentDAOI.getLikeByCommentGroupEntry(lbc.getCommentId(), lbc.getArticleId(), lbc.getUserId(), lbc.getCreatedDate());
            likeByCommentGroup.ifPresent(lbcg -> {commentLikeHandlerHelperBean.setCurrentLikeByCommentGroup(lbcg);});
        });
    }

    protected void populateNewCommentUserLikeDate(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean, LikeRequestBean likeRequestBean){
        if (likeRequestBean.getStatus() == ArticleConst.USER_LIKED || likeRequestBean.getStatus() == ArticleConst.USER_UNLIKED){
            commentLikeHandlerHelperBean.setNewLikeByComment(new LikeByComment(likeRequestBean));
            commentLikeHandlerHelperBean.setNewLikeByCommentGroup(new LikeByCommentGroup(likeRequestBean));
        }
    }


}

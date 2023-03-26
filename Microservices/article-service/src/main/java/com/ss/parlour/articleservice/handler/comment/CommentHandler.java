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

    /***
     * Process for user added comment on article
     * @param commentBean
     * @return comment id
     */
    @Override
    public CommentCommonResponse processAddCommentRequest(CommentBean commentBean){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        CommentAddHelperBean commentAddHelperBean = new CommentAddHelperBean();
        prePopulateCommentId(commentBean); //Generate comment id if not exists
        prePopulateCommentCreatedDate(commentBean); //Add comment created date if not exists
        populateCommentBean(commentAddHelperBean, commentBean); //Populate comment bean
        populateCommentGroup(commentAddHelperBean, commentBean); //Populate comment group
        commentDAOI.saveComment(commentAddHelperBean); //Save comment details into table
        commentCommonResponse.setCommentId(commentBean.getCommentId());
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_ADDED);
        return commentCommonResponse;
    }

    /***
     * Delete comment for article
     * @param commentDeleteRequest
     */
    @Override
    public CommentCommonResponse processDeleteCommentRequest(CommentDeleteRequest commentDeleteRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        CommentDeleteHelperBean commentDeleteHelperBean = new CommentDeleteHelperBean();
        Comment comment = populateCommentFromDb(commentDeleteRequest);
        Optional<List<CommentGroup>> currentCommentGroupList = populateCommentGroupFromDb(comment.getArticleId(), comment.getCommentId());
        commentDeleteHelperBean.setComment(comment);
        currentCommentGroupList.ifPresent(commentGroups -> {commentDeleteHelperBean.setCommentGroup(commentGroups);});
        commentDAOI.deleteComment(commentDeleteHelperBean);
        commentCommonResponse.setCommentId(commentDeleteRequest.getCommentDeleteRequestInner().getCommentId());
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_DELETED);
        return commentCommonResponse;
    }

    /***
     * Process of user invoke upvote or downvote to comment
     * @param likeRequestBean
     */
    @Override
    public void addLikeRequest(LikeRequestBean likeRequestBean){
        CommentLikeHandlerHelperBean commentLikeHandlerHelperBean = new CommentLikeHandlerHelperBean();
        likeRequestBean.setCreatedDate(DateUtils.currentSqlTimestamp());
        populateCurrentUserCommentLikeStatus(commentLikeHandlerHelperBean, likeRequestBean);
        populateNewCommentUserLikeDate(commentLikeHandlerHelperBean, likeRequestBean);
        commentDAOI.updateArticleUserLikeRequest(commentLikeHandlerHelperBean);
    }

    @Override
    public CommentResponse findArticleComments(CommentRequest commentRequest){
        CommentRequest.CommentRequestInner commentRequestInner = commentRequest.getCommentRequestInner();
        CommentResponse commentResponse = new CommentResponse();
        List<CommentBean> commentList = new ArrayList<>();
        Optional<List<CommentGroup>> currentCommentGroupList = populateCommentGroupFromDb(commentRequestInner.getArticleId(), commentRequestInner.getParentCommentId());
        currentCommentGroupList.ifPresent(commentGroupList -> commentGroupList.forEach(listItem -> commentList.add(new CommentBean(listItem)))); //Populate top level comment items
        populateChildComments(commentList); //Populate sub child comment lists
        commentResponse.setArticleComments(commentList);
        commentResponse.setParentCommentId(commentRequestInner.getParentCommentId());
        commentResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentResponse.setNarration(ArticleConst.ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION);
        return commentResponse;
    }

    protected void populateChildComments(List<CommentBean> commentList){
        commentList.forEach(commentBean -> loadAndAssignChildComments(commentBean));
    }

    protected void loadAndAssignChildComments(CommentBean commentBean){
        List<CommentBean> commentChildList = new ArrayList<>();
        commentBean.setSubCommentBean(commentChildList);
        Optional<List<CommentGroup>> currentCommentGroupList = populateCommentGroupFromDb(commentBean.getArticleId(), commentBean.getCommentId());
        currentCommentGroupList.ifPresent(commentGroupList -> commentGroupList.forEach(listItem -> commentBean.getSubCommentBean().add(new CommentBean(listItem))));
        if (!commentChildList.isEmpty()){
            commentChildList.forEach(childCommentBean -> loadAndAssignChildComments(childCommentBean));
        }
    }

    protected void prePopulateCommentId(CommentBean commentBean){
        if(commentBean.getCommentId() == null || commentBean.getCommentId().isEmpty()){
            commentBean.setCommentId(keyGenerator.commentKeyGenerator(commentBean.getUserId(), commentBean.getCommentId()));
        }
    }

    protected void prePopulateCommentCreatedDate(CommentBean commentBean){
        Optional<Comment> currentComment = commentDAOI.getCommentByIdAndArticleId(commentBean.getCommentId(), commentBean.getCommentId());
        currentComment.ifPresentOrElse(comment -> commentBean.setCreatedDate(comment.getCreatedDate()),
                () -> commentBean.setCreatedDate(DateUtils.currentSqlTimestamp()));
    }

    protected void populateCommentBean(CommentAddHelperBean commentAddHelperBean, CommentBean commentBean){
        Comment comment = new Comment(commentBean);
        commentAddHelperBean.setComment(comment);
    }

    protected void populateCommentGroup(CommentAddHelperBean commentAddHelperBean, CommentBean commentBean){
        CommentGroup commentGroup = new CommentGroup(commentBean);
        commentAddHelperBean.setCommentGroup(commentGroup);
    }

    protected Comment populateCommentFromDb(CommentDeleteRequest commentDeleteRequest){
        CommentDeleteRequest.CommentDeleteRequestInner commentDeleteRequestInner = commentDeleteRequest.getCommentDeleteRequestInner();
        Optional<Comment> currentComment = commentDAOI.getCommentByIdAndArticleId(commentDeleteRequestInner.getCommentId(), commentDeleteRequestInner.getArticleId());
        var commentInDb = currentComment.orElseThrow(
                () -> new ArticleServiceRuntimeException(String.format(ArticleErrorCodes.COMMENT_NOT_FOUND_ERROR,
                        commentDeleteRequestInner.getCommentId(), commentDeleteRequestInner.getArticleId())));
        return commentInDb;
    }

    protected Optional<List<CommentGroup>> populateCommentGroupFromDb(String articleId, String commentId){
        return commentDAOI.getCommentGroup(articleId, commentId);
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

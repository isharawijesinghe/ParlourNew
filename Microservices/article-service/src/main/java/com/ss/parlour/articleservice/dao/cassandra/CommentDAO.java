package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.repository.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.*;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class CommentDAO implements CommentDAOI {

    @Autowired
    private CommentRepositoryI commentRepositoryI;

    @Autowired
    private CommentGroupRepositoryI commentGroupRepositoryI;

    @Autowired
    private LikeByCommentRepositoryI likeByCommentRepositoryI;

    @Autowired
    private LikeByCommentGroupRepositoryI likeByCommentGroupRepositoryI;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public Optional<Comment> getCommentById(String commentId) {
        return commentRepositoryI.findById(commentId);
    }

    @Override
    public Optional<Comment> getCommentByIdAndArticleId(String commentId, String articleId) {
        return commentRepositoryI.findByCommentIdAndArticleId(commentId, articleId);
    }

    @Override
    public Optional<List<CommentGroup>> getCommentGroup(String articleId, String parentId) {
        return commentGroupRepositoryI.findByArticleIdAndParentId(articleId, parentId);
    }

    @Override
    public void saveComment(CommentAddHelperBean commentAddHelperBean) {
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertAddCommentRequestInBatch(commentAddHelperBean, batchOps);
    }

    @Override
    public void deleteComment(CommentDeleteHelperBean commentDeleteHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        deleteCommentInBatch(commentDeleteHelperBean, batchOps);
    }

    @Override
    public Optional<LikeByComment> getLikeByCommentEntry(String commentId, String articleId, String userId){
        return likeByCommentRepositoryI.findByCommentIdAndArticleIdAndUserId(commentId, articleId, userId);
    }

    @Override
    public Optional<LikeByCommentGroup> getLikeByCommentGroupEntry(String commentId, String articleId, String userId, Timestamp createdDate){
        return likeByCommentGroupRepositoryI.findByCommentIdAndArticleIdAndUserIdAndCreatedDate(commentId, articleId, userId, createdDate);
    }

    @Override
    public void updateArticleUserLikeRequest(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleUserLikeRequestInBatch(commentLikeHandlerHelperBean, batchOps);
        deleteArticleUserLikeRequestInBatch(commentLikeHandlerHelperBean, batchOps);
    }

    protected void insertArticleUserLikeRequestInBatch(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean, CassandraBatchOperations batchOps){
        if (commentLikeHandlerHelperBean.getNewLikeByComment() != null){
            batchOps.insert(commentLikeHandlerHelperBean.getNewLikeByComment());
        }

        if (commentLikeHandlerHelperBean.getNewLikeByCommentGroup() != null){
            batchOps.insert(commentLikeHandlerHelperBean.getNewLikeByCommentGroup());
        }
    }

    protected void deleteArticleUserLikeRequestInBatch(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean, CassandraBatchOperations batchOps){
        if (commentLikeHandlerHelperBean.getCurrentLikeByComment() != null){
            batchOps.delete(commentLikeHandlerHelperBean.getCurrentLikeByComment());
        }

        if (commentLikeHandlerHelperBean.getCurrentLikeByCommentGroup() != null){
            batchOps.delete(commentLikeHandlerHelperBean.getCurrentLikeByCommentGroup());
        }
    }

    protected void insertAddCommentRequestInBatch(CommentAddHelperBean commentAddHelperBean, CassandraBatchOperations batchOps) {
        batchOps.insert(commentAddHelperBean.getComment());
        batchOps.insert(commentAddHelperBean.getCommentGroup());
        batchOps.execute();
    }

    protected void deleteCommentInBatch(CommentDeleteHelperBean commentDeleteHelperBean, CassandraBatchOperations batchOps){
        Comment comment = commentDeleteHelperBean.getComment();
        List<CommentGroup> commentGroupsList = commentDeleteHelperBean.getCommentGroup();
        deleteParentCommentFromDb(comment, batchOps);
        commentGroupsList.forEach(g -> deleteAllChildComments(g, batchOps));
        batchOps.execute();
    }

    //Recursively delete all nodes for parent
    protected void deleteAllChildComments(CommentGroup commentGroup, CassandraBatchOperations batchOps){
        deleteChildCommentsFromDb(commentGroup, batchOps); //Delete top node
        Optional<List<CommentGroup>> commentChildGroupList = getCommentGroup(commentGroup.getArticleId(), commentGroup.getCommentId());
        if (commentChildGroupList.isPresent() && commentChildGroupList.get().size() > ArticleConst.ARTICLE_NUMBER_ZERO){ //Check for available child nodes
            commentChildGroupList.get().forEach(g -> deleteAllChildComments(g, batchOps));
        }
    }

    //Delete parent comments from db
    protected void deleteParentCommentFromDb(Comment comment, CassandraBatchOperations batchOps){
        batchOps.delete(commentRepositoryI.findByCommentIdAndArticleId(comment.getCommentId(), comment.getArticleId()).get());
        batchOps.delete(commentGroupRepositoryI.findByArticleIdAndParentIdAndCreatedDate(
                comment.getArticleId(), comment.getParentId(), comment.getCreatedDate()).get());
    }

    //Delete child comments from db
    protected void deleteChildCommentsFromDb(CommentGroup commentGroup, CassandraBatchOperations batchOps){
        batchOps.delete(commentGroupRepositoryI.findByArticleIdAndParentIdAndCreatedDate(
                        commentGroup.getArticleId(), commentGroup.getParentId(), commentGroup.getCreatedDate()).get()
        );
        batchOps.delete(commentRepositoryI.findByCommentIdAndArticleId(commentGroup.getCommentId(), commentGroup.getArticleId()).get());
    }

}


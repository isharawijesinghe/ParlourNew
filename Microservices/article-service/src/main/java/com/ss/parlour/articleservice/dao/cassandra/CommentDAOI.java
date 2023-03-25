package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;

import com.ss.parlour.articleservice.domain.cassandra.CommentGroup;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;
import com.ss.parlour.articleservice.domain.cassandra.LikeByCommentGroup;
import com.ss.parlour.articleservice.utils.bean.CommentAddHelperBean;
import com.ss.parlour.articleservice.utils.bean.CommentDeleteHelperBean;
import com.ss.parlour.articleservice.utils.bean.CommentLikeHandlerHelperBean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CommentDAOI {

    void saveComment(CommentAddHelperBean commentAddHelperBean);
    void deleteComment(CommentDeleteHelperBean commentDeleteHelperBean);
    Optional<Comment> getCommentById(String commentId);
    Optional<Comment> getCommentByIdAndArticleId(String commentId, String articleId);
    Optional<List<CommentGroup>> getCommentGroup(String articleId, String parentId);
    Optional<LikeByComment> getLikeByCommentEntry(String commentId, String articleId, String userId);
    Optional<LikeByCommentGroup> getLikeByCommentGroupEntry(String commentId, String articleId, String userId, Timestamp createdDate);
    void updateArticleUserLikeRequest(CommentLikeHandlerHelperBean commentLikeHandlerHelperBean);

}

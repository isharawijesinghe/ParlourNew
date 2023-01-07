package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.CommentByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;

import java.util.List;
import java.util.Optional;

public interface CommentDAOI {

    void saveComment(Comment comment);
    Optional<Comment> getCommentById(String commentId);
    Optional<CommentByArticle> getCommentsByArticleId(String articleId);
    void saveCommentByArticle(CommentByArticle comment_by_article);
    void updateCommentListByArticle(List<Comment> commentList, String articleId);
    Optional<LikeByComment> getLikeByComment(String commentId, String articleId);
}

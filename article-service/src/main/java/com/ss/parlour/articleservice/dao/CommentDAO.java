package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.CommentByArticle;
import com.ss.parlour.articleservice.repository.cassandra.CommentByArticleRepositoryI;
import com.ss.parlour.articleservice.repository.cassandra.CommentRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentDAO implements CommentDAOI{

    @Autowired
    private CommentRepositoryI commentRepositoryI;

    @Autowired
    private CommentByArticleRepositoryI commentByArticleRepositoryI;

    @Override
    public Optional<Comment> getCommentById(String commentId){
        return commentRepositoryI.findById(commentId);
    }

    @Override
    public Optional<CommentByArticle> getCommentsByArticleId(String articleId){
        return commentByArticleRepositoryI.findById(articleId);
    }

    @Override
    public void saveComment(Comment comment){
        commentRepositoryI.save(comment);
    }

    @Override
    public void saveCommentByArticle(CommentByArticle comment_by_article){
        commentByArticleRepositoryI.save(comment_by_article);
    }

    public void updateCommentListByArticle(List<Comment> commentList, String articleId){
        commentByArticleRepositoryI.updateCommentListByArticle(commentList, articleId);
    }
}

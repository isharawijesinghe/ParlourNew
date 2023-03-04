package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;

import java.util.List;

public interface CommentHandlerI {

    Comment processAddCommentRequest(CommentBean commentBean);
    List<Comment> getCommentListForPost(ArticleRequest articleRequest);
    void processDeleteCommentRequest(CommentDeleteRequest commentDeleteRequest);
}

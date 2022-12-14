package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequestBean;

import java.util.List;

public interface CommentHandlerI {

    Comment handleCommentRequest(CommentBean commentBean);
    List<Comment> getCommentListForPost(ArticleRequestBean articleRequestBean);
    void deleteComment(CommentDeleteRequestBean commentDeleteRequestBean);
}

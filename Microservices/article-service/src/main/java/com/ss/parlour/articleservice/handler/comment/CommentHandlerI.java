package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;
import com.ss.parlour.articleservice.utils.bean.response.CommentCommonResponse;

import java.util.List;

public interface CommentHandlerI {

    CommentCommonResponse processAddCommentRequest(CommentBean commentBean);
    List<Comment> getCommentListForPost(String articleId);
    CommentCommonResponse processDeleteCommentRequest(CommentDeleteRequest commentDeleteRequest);
}

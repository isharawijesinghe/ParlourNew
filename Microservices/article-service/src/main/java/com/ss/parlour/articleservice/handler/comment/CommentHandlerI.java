package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequest;

import java.util.List;

public interface CommentHandlerI {

    String processAddCommentRequest(CommentBean commentBean);
    void processDeleteCommentRequest(CommentDeleteRequest commentDeleteRequest);
    List<CommentBean> findArticleComments(CommentRequest commentRequest);
}

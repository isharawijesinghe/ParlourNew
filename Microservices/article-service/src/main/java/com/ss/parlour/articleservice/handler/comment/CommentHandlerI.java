package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequest;
import com.ss.parlour.articleservice.utils.bean.response.CommentCommonResponse;
import com.ss.parlour.articleservice.utils.bean.response.CommentResponse;

import java.util.List;

public interface CommentHandlerI {

    CommentCommonResponse processAddCommentRequest(CommentBean commentBean);
    CommentCommonResponse processDeleteCommentRequest(CommentDeleteRequest commentDeleteRequest);
    CommentResponse findArticleComments(CommentRequest commentRequest);
}

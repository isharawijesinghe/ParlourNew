package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.utils.bean.CommentAddHelperBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.CommentDeleteHelperBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequest;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequest;
import com.ss.parlour.articleservice.utils.bean.response.CommentCommonResponse;
import com.ss.parlour.articleservice.utils.bean.response.CommentResponse;

import java.util.List;

public interface CommentHandlerI {


    CommentResponse findArticleComments(CommentRequest commentRequest);
    void prePopulateCommentId(CommentBean commentBean);
    void prePopulateCommentCreatedDate(CommentBean commentBean);
    CommentAddHelperBean populateCommentAddHelperBean(CommentBean commentBean);
    CommentDeleteHelperBean populateCommentDeleteHelperBean(CommentDeleteRequest commentDeleteRequest);
}

package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentBean {
    private String id;
    private String articleId;
    private String parentId;
    private String authorName;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<CommentBean> subCommentBean;

}

package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.CommentGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentBean {
    private String commentId;
    private String articleId;
    private String parentId;
    private String userId;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<CommentBean> subCommentBean = new ArrayList<>();

    public CommentBean(CommentGroup commentGroup){
        this.commentId = commentGroup.getCommentId();
        this.articleId = commentGroup.getArticleId();
        this.parentId = commentGroup.getParentId();
        this.userId = commentGroup.getUserId();
        this.content = commentGroup.getContent();
        this.createdDate = commentGroup.getCreatedDate();
        this.modifiedDate = commentGroup.getModifiedDate();

    }

}

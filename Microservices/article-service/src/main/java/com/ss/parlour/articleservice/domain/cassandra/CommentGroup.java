package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.CommentBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("comment_group")
@Getter
@Setter
@NoArgsConstructor
public class CommentGroup {

    private String commentId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String parentId;
    private String userId;
    private String content;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public CommentGroup(CommentBean commentBean){
        this.commentId = commentBean.getCommentId();
        this.articleId = commentBean.getArticleId();
        this.parentId = commentBean.getParentId();
        this.userId = commentBean.getUserId();
        this.content = commentBean.getContent();
        this.createdDate = commentBean.getCreatedDate();
        this.modifiedDate = commentBean.getModifiedDate();
    }

}

package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.CommentBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Timestamp;

@Table("comment")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String commentId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    private String parentId;
    private String userId;
    private String content;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Comment(CommentBean commentBean){
        this.commentId = commentBean.getCommentId();
        this.articleId = commentBean.getArticleId();
        this.parentId = commentBean.getParentId();
        this.userId = commentBean.getUserId();
        this.content = commentBean.getContent();
        this.createdDate = commentBean.getCreatedDate();
        this.modifiedDate = commentBean.getModifiedDate();
    }

}

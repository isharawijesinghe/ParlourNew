package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.HashMap;

@Table("like_by_comment")
@Getter
@Setter
@NoArgsConstructor
public class LikeByComment {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String commentId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    private Timestamp createdDate;

    public LikeByComment(LikeRequestBean likeRequestBean){
        this.commentId = likeRequestBean.getCommentId();
        this.articleId = likeRequestBean.getArticleId();
        this.userId = likeRequestBean.getUserId();
        this.createdDate = likeRequestBean.getCreatedDate();
    }

}

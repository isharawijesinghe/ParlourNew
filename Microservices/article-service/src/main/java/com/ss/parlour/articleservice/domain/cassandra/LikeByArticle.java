package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("like_by_article")
@Getter
@Setter
@NoArgsConstructor
public class LikeByArticle {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    private Timestamp createdDate;

    public LikeByArticle(LikeRequestBean likeRequestBean){
        this.articleId = likeRequestBean.getArticleId();
        this.userId = likeRequestBean.getUserId();
        this.createdDate = likeRequestBean.getCreatedDate();
    }
}

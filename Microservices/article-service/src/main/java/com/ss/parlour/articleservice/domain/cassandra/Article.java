package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.*;

@Table("article")
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    private String userId;
    private String title;
    private String summary;
    private String content;
    private String status;
    private String categoryId;
    private String thumbnailUrl;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Article(ArticleBean articleBean){
        this.articleId = articleBean.getArticleId();
        this.userId = articleBean.getUserId();
        this.title = articleBean.getTitle();
        this.summary = articleBean.getSummary();
        this.content = articleBean.getSummary();
        this.status = articleBean.getStatus();
        this.categoryId = articleBean.getCategoryId();
        this.thumbnailUrl = articleBean.getThumbnailUrl();
        this.createdDate = articleBean.getCreatedDate();
        this.modifiedDate = articleBean.getModifiedDate();
    }


}

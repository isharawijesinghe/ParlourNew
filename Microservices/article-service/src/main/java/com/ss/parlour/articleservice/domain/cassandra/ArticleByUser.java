package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("article_by_user")
@Getter
@Setter
@NoArgsConstructor
public class ArticleByUser {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String articleId;
    private String title;
    private String summary;
    private String content;
    private String status;
    private String categoryId;
    private String thumbnailUrl;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public ArticleByUser(ArticleBean articleBean){
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

    public ArticleByUser(Article article){
        this.articleId = article.getArticleId();
        this.userId = article.getUserId();
        this.title = article.getTitle();
        this.summary = article.getSummary();
        this.content = article.getSummary();
        this.status = article.getStatus();
        this.categoryId = article.getCategoryId();
        this.thumbnailUrl = article.getThumbnailUrl();
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
    }
}

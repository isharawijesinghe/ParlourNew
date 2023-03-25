package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ArticleBean {
    private String articleId;
    private String userId;
    private String authorName;
    private String title;
    private String summary;
    private String status;
    private String content;
    private String categoryId;
    private String thumbnailUrl;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}

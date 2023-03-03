package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ArticleBean {
    private String id;
    private String authorName;
    private String title;
    private String summary;
    private int status;
    private String content;
    private String categoryId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}

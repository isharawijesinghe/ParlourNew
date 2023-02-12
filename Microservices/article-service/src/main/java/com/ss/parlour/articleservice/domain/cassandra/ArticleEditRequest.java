package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.Table;

@Table("articleeditrequest")
public class ArticleEditRequest {


    private String articleId;
    private String requestUserId;
    private String originalAuthorId;
    private String requestMessage;
}

package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditBean {

    private String editRequestId;
    private String articleId;
    private String title;
    private String requestUserId;
    private String description;
    private String editRequestStatus;
    private String ownerId;
}

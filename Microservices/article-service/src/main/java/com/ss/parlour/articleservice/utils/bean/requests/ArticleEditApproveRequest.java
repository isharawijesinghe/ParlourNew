package com.ss.parlour.articleservice.utils.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditApproveRequest {

    private String editRequestId;
    private String articleId;
    private String title;
    private String requestUserId;
    private String ownerId;
}
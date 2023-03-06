package com.ss.parlour.articleservice.utils.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDeleteRequest {

    private String articleId;
    private String userName;

}

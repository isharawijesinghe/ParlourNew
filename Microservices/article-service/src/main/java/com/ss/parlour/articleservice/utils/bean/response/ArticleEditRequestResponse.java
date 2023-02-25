package com.ss.parlour.articleservice.utils.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequestResponse {

    private String editRequestId;
    private int status;
    private String narration;
}

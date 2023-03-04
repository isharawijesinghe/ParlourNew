package com.ss.parlour.articleservice.utils.bean.requests;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditDraftRequest {

    private String articleId;
    private String editRequestId;
    private ArticleBean articleBean;
}

package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.EditDraftArticles;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditDraftResponse {

    private String articleId;
    private String editRequestId;
}

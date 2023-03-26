package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditDraftRequest extends ArticleHeader {

    @JsonProperty("body")
    private ArticleEditDraftRequestInner articleEditDraftRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class  ArticleEditDraftRequestInner{
        private String articleId;
        private String editRequestId;
        private ArticleBean articleBean;
    }


}

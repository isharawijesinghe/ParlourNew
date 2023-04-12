package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticlePublishEditDraftRequest extends ArticleHeader {

    @JsonProperty("body")
    private ArticlePublishEditDraftInner articlePublishEditDraftInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ArticlePublishEditDraftInner{
        private String editRequestId;
        private String articleId;
    }
}

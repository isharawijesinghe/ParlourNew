package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleHistoryRequest extends ArticleHeader {

    @JsonProperty("body")
    private ArticleHistoryRequestInner articleHistoryRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ArticleHistoryRequestInner{
        private String articleId;
    }


}

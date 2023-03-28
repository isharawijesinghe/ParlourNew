package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDeleteRequest extends ArticleHeader {

    @JsonProperty("body")
    private ArticleDeleteRequestInner articleDeleteRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ArticleDeleteRequestInner{
        private String articleId;
        private String userId;
    }

}

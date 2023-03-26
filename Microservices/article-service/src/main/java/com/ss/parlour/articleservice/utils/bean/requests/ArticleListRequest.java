package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleListRequest extends ArticleHeader {


    @JsonProperty("body")
    private ArticleListRequestInner articleListRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ArticleListRequestInner{
        private String loginName;
        private List<String> allowStatusFilter;
        private List<String> removeStatusFilter;
    }

}

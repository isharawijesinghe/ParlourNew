package com.ss.parlour.articleservice.utils.bean.requests;

import com.amazonaws.partitions.PartitionRegionImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditApproveRequest extends ArticleHeader {

    @JsonProperty("body")
    private ArticleEditApproveRequestInner articleEditApproveRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ArticleEditApproveRequestInner{
        private String editRequestId;
        private String articleId;
    }


}

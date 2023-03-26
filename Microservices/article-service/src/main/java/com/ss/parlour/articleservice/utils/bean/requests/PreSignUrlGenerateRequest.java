package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreSignUrlGenerateRequest extends ArticleHeader {

    @JsonProperty("body")
    private PreSignUrlGenerateRequestInner preSignUrlGenerateRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class PreSignUrlGenerateRequestInner{
        private String extension;
    }


}

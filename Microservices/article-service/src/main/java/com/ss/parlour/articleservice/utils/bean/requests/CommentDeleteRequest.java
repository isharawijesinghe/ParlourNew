package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDeleteRequest extends ArticleHeader {

    @JsonProperty("body")
    private CommentDeleteRequestInner commentDeleteRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class CommentDeleteRequestInner{
        private String articleId;
        private String commentId;
    }


}

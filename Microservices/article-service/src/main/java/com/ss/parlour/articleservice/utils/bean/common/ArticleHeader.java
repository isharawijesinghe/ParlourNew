package com.ss.parlour.articleservice.utils.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class ArticleHeader {

    @JsonProperty("header")
    private ArticleMsgHeader articleMsgHeader;
}

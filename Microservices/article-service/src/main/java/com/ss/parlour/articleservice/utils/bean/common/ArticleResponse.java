package com.ss.parlour.articleservice.utils.bean.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ArticleResponse<T> extends ArticleHeader {

    private String message;
    private int httpStatus = 200;
    private ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Z"));
    private T body;
}

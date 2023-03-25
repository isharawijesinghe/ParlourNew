package com.ss.parlour.streamservice.utils.bean;

import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleMappingHelperBean {

    private StreamMappedArticles streamMappedArticles;
    private ArticleMappedStream articleMappedStream;
}

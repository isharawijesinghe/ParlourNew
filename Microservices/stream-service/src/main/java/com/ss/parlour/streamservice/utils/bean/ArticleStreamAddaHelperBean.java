package com.ss.parlour.streamservice.utils.bean;

import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleStreamAddaHelperBean {

    private List<ArticleMappedStream> articleMappedStreamList = new ArrayList<>();
    private List<StreamMappedArticles> streamMappedArticlesList = new ArrayList<>();
}

package com.ss.parlour.streamservice.utils.bean.response;

import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StreamMappedArticleResponse {

    private List<StreamMappedArticles> streamMappedArticles;

}

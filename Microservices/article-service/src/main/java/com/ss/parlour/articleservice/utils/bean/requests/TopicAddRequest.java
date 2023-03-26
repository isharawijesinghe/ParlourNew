package com.ss.parlour.articleservice.utils.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.articleservice.domain.cassandra.Topics;
import com.ss.parlour.articleservice.utils.bean.common.ArticleHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicAddRequest extends ArticleHeader {

    @JsonProperty("body")
    private TopicAddRequestInner topicAddRequestInner;

    @Getter
    @Setter
    @NoArgsConstructor
    public class TopicAddRequestInner{
        private List<Topics> topicName;
    }

}

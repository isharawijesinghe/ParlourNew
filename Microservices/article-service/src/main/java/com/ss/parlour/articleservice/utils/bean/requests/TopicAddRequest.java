package com.ss.parlour.articleservice.utils.bean.requests;

import com.ss.parlour.articleservice.domain.cassandra.Topics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicAddRequest {

    private List<Topics> topicName;
}

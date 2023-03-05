package com.ss.parlour.userservice.util.bean.requests;

import com.ss.parlour.userservice.domain.cassandra.Topics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsAddRequest {

    private List<Topics> topicName;
}

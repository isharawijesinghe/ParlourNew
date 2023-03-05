package com.ss.parlour.userservice.util.bean.response;

import com.ss.parlour.userservice.domain.cassandra.Topics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsResponse {

    private List<Topics> topicName = new ArrayList<>();
}

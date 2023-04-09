package com.ss.parlour.userservice.util.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsResponse {

    private String userId;
    private List<String> topicName = new ArrayList<>();
}

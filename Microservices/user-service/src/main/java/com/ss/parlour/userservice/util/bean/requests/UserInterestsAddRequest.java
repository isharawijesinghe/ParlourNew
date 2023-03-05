package com.ss.parlour.userservice.util.bean.requests;

import com.ss.parlour.userservice.domain.cassandra.Topics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsAddRequest implements Serializable {

    public String loginName;
    public List<Topics> topicName;

}

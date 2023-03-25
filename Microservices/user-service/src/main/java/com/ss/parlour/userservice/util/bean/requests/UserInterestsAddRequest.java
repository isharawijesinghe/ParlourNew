package com.ss.parlour.userservice.util.bean.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.parlour.userservice.util.bean.common.UserHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsAddRequest extends UserHeader implements Serializable{

    @JsonProperty("body")
    private UserInterestsAddRequestBody userInterestsAddRequestBody;

    @Getter
    @Setter
    public class UserInterestsAddRequestBody implements Serializable {
        private String userId;
        private List<String> topicName = new ArrayList<>();
        private Timestamp createdDate;
    }


}

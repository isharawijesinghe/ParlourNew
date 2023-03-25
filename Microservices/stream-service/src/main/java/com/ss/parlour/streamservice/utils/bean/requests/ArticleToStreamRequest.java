package com.ss.parlour.streamservice.utils.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleToStreamRequest {

    private String streamId;
    private String userId;
    private List<String> articleIdList = new ArrayList<>();
    private Timestamp createdDate;

}

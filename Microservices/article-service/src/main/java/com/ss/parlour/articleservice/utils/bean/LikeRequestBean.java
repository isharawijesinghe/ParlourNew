package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class LikeRequestBean {

    private String articleId;
    private String commentId;
    private String userName;
    private int status;
    private LikeType likeType;
    private String userId;
    private Timestamp createdDate;

}

package com.ss.parlour.articleservice.utils.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeBean {

    private String articleId;
    private String commentId;
    private String userName;
    private int status;
    private LikeType likeType;

}

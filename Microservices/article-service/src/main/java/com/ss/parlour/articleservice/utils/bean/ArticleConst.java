package com.ss.parlour.articleservice.utils.bean;

public interface ArticleConst {

    int STATUS_SUCCESS = 1;
    String SUCCESSFULLY_CREATED_ARTICLE = "Successfully created article";
    String SUCCESSFULLY_COMMENT_ADDED = "Successfully comment added";
    String SEPARATOR = "~";

    int USER_LIKED = 1;
    int USER_UNLIKED = -1;
    int USER_NEUTRAL = 0;

    int ARTICLE_ACTIVE = 1;
    int ARTICLE_INACTIVE = -1;

    int COMMENT_ACTIVE = 1;
    int COMMENT_INACTIVE = -1;

    String NO_PARENT_COMMENT = "-1";


}

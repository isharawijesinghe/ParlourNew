package com.ss.parlour.articleservice.utils.bean;

public interface ArticleConst {

    String ARTICLE_TYPE = "ARTIC";
    String COMMENT_TYPE = "COMM";
    String LIKE_TYPE = "LIKE";

    int STATUS_SUCCESS = 1;
    String SUCCESSFULLY_CREATED_ARTICLE = "Successfully created article";
    String SUCCESSFULLY_COMMENT_ADDED = "Successfully comment added";
    String SUCCESSFULLY_LIKE_ADDED = "Successfully like added";
    String SUCCESSFULLY_ARTICLE_DELETED = "Successfully article deleted";
    String SUCCESSFULLY_COMMENT_DELETED = "Successfully comment deleted";
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

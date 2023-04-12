package com.ss.parlour.articleservice.utils.bean;

public interface ArticleConst {

    public final static String DEFAULT_TENANCY_CODE = "DEFAULT_TENANCY_CODE";

    String ARTICLE_TYPE = "ARTIC";
    String COMMENT_TYPE = "COMM";
    String LIKE_TYPE = "LIKE";

    int STATUS_SUCCESS = 1;
    String SUCCESSFULLY_CREATED_ARTICLE = "Successfully created article";
    String SUCCESSFULLY_COMMENT_ADDED = "Successfully comment added";
    String SUCCESSFULLY_LIKE_ADDED = "Successfully like added";
    String SUCCESSFULLY_LOAD_ARTICLE_DETAILS = "Successfully article details loaded";
    String SUCCESSFULLY_ARTICLE_DELETED = "Successfully article deleted";
    String SUCCESSFULLY_COMMENT_DELETED = "Successfully comment deleted";
    String SUCCESSFULLY_PLACE_EDIT_REQUEST = "Edit request successful";
    String SUCCESSFULLY_APPROVED_EDIT_REQUEST = "Edit request successfully approved";
    String ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION = "Successfully saved article edit draft";
    String ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION = "Topics added successfully";
    String ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION = "Successfully loaded article comments";
    String ARTICLE_HISTORY_SEARCH_SUCCESSFUL_NARRATION = "Successfully loaded article history";
    String ARTICLE_IMAGE_UPLOAD_PRE_SIGNED_GENERATE_SUCCESSFUL_NARRATION = "Successfully generate pre sign url for article image upload";
    String SUCCESSFULLY_LOADED_EDIT_APPROVE_REQUESTS = "Successfully loaded article edit approve requests for users";

    String SEPARATOR = "~";

    int USER_LIKED = 1;
    int USER_UNLIKED = -1;
    int USER_NEUTRAL = 0;

    String ARTICLE_ACTIVE = "Active";
    String ARTICLE_MARK_DELETED = "Mark deleted";

    int COMMENT_ACTIVE = 1;
    int COMMENT_INACTIVE = -1;

    String ARTICLE_PARENT_COMMENT_NO = "-1";

    String ARTICLE_EDIT_REQUEST_PENDING = "Pending";
    String ARTICLE_EDIT_REQUEST_APPROVED = "Edit Approved";
    String SHARED_WITH_USER_PENDING = "Share article pending";
    String SHARED_WITH_USER_ARTICLE_DRAFT = "Shared article drafted";
    String SHARED_WITH_USER_ARTICLE_PUBLISHED = "Shared article published";
    String ARTICLE_EDIT_REQUEST_IN_REVIEW = "Review In Progress";

    String AWS_CLOUD_PROVIDER = "AWS";

    String ARTICLE_STATUS_DRAFT = "Draft";
    String ARTICLE_STATUS_EDIT_DRAFT_SUBMIT = "Draft submitted";
    String ARTICLE_STATUS_UNDER_REVIEW = "Under review";
    String ARTICLE_STATUS_REVIEW_IN_PROGRESS = "Review in progress";
    String ARTICLE_STATUS_ACCEPTED = "Accepted";
    String ARTICLE_STATUS_PUBLISHED = "Published";

    int ARTICLE_NUMBER_ZERO = 0;

    String COMMENT_DEFAULT_ROOT_PARENT = "-1";
    int COMMENT_DEFAULT_DEPTH = 2;
    int COMMENT_DEFAULT_COMMENT_COUNT = 5;
    int COMMENT_DEFAULT_DEPTH_COUNT = 2;

    boolean STATUS_TRUE = true;
    boolean STATUS_FALSE = false;

    int NUMBER_ZERO = 0 ;


}

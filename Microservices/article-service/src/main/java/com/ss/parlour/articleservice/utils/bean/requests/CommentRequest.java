package com.ss.parlour.articleservice.utils.bean.requests;

import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {

    private String articleId;
    private String parentCommentId = ArticleConst.COMMENT_DEFAULT_ROOT_PARENT;
    private int from;
    private int count = ArticleConst.COMMENT_DEFAULT_COMMENT_COUNT;//Number of top leve comments to be loaded
    private int depth = ArticleConst.COMMENT_DEFAULT_DEPTH;//Number of child comments needed to be loaded
    private int depthCount = ArticleConst.COMMENT_DEFAULT_DEPTH_COUNT;;//Total number of child comments needed to be loaded

}

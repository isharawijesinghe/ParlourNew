package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticleGroup;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;
import com.ss.parlour.articleservice.domain.cassandra.LikeByCommentGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentLikeHandlerHelperBean {

    private LikeByComment currentLikeByComment;
    private LikeByCommentGroup currentLikeByCommentGroup;
    private LikeByComment newLikeByComment;
    private LikeByCommentGroup newLikeByCommentGroup;
}

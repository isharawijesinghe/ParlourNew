package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticleGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleLikeHandlerHelperBean {

    private LikeByArticle currentLikeByArticle;
    private LikeByArticleGroup currentLikeByArticleGroup;
    private LikeByArticle newLikeByArticle;
    private LikeByArticleGroup newLikeByArticleGroup;
}

package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.ArticleByUser;
import com.ss.parlour.articleservice.domain.cassandra.ArticleHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleUpdateHelperBean {

    private Article updatedArticle;
    private ArticleHistory articleHistory;
    private ArticleByUser articleByUser;
}

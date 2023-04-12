package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditPublishHelperBean {

    private Article oldArticle;
    private EditRequest editRequest;
    private EditDraftArticles editDraftArticles;
    private SharedArticles sharedArticles;
    private SharedArticlesWithUser sharedArticlesWithUser;
    private Article updatedArticle;
    private ArticleByUser articleByUser;
    private ArticleContributors articleContributors;
}

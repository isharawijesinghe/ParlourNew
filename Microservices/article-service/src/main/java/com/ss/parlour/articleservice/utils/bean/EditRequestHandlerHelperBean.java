package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditRequestHandlerHelperBean {

    private EditRequest editRequest;
    private EditRequestByArticle editRequestByArticle;
    private EditRequestByUser editRequestByUser;
    private SharedArticles sharedArticles;
    private SharedArticlesWithUser sharedArticlesWithUser;
    private EditDraftArticles editDraftArticles;
    private EditArticleContributors editArticleContributors;
}

package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.EditDraftArticles;
import com.ss.parlour.articleservice.domain.cassandra.SharedArticles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditDraftArticleHelper {

    private SharedArticles sharedArticles;
    private EditDraftArticles editDraftArticles;

}

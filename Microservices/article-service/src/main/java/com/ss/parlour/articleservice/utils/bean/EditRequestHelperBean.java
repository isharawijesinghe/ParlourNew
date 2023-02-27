package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.EditRequest;
import com.ss.parlour.articleservice.domain.cassandra.EditRequestByArticle;
import com.ss.parlour.articleservice.domain.cassandra.EditRequestByUser;
import com.ss.parlour.articleservice.domain.cassandra.SharedArticles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditRequestHelperBean {

    private EditRequest editRequest;
    private EditRequestByArticle editRequestByArticle;
    private EditRequestByUser editRequestByUser;
    private SharedArticles sharedArticles;
}

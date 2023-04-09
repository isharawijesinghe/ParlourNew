package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.ArticleByUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleListResponse {

    private List<ArticleByUser> articleResponseList = new ArrayList<>();
}

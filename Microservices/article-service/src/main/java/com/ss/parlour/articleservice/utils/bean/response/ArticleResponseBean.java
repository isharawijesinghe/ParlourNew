package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponseBean {

    private Article article;
    private List<Comment> articleComments = new ArrayList<>();
    private AuthorDetailResponseBean authorDetails;

}

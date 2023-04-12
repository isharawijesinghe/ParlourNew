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
public class ArticleDetailsResponse {

    private Article article;
    private List articleComments = new ArrayList<>();
    private UserDetailResponse authorDetails;
    private List<UserDetailResponse> contributors = new ArrayList<>();

}

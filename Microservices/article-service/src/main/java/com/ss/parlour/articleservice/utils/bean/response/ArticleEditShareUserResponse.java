package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.SharedArticlesWithUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditShareUserResponse {

    private List<SharedArticlesWithUser> sharedArticlesWithUsers = new ArrayList<>();
}

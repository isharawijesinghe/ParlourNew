package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Table("shared_articles")
public class SharedArticles {

    @PrimaryKey
    private String userId;
    private HashMap<String, SharedArticleBean> sharedArticleBeanMap = new HashMap<>();

}

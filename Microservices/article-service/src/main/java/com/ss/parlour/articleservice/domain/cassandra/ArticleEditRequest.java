package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("articleeditrequest")
@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequest {

    @PrimaryKey
    private String articleId;
    private HashMap<String, ArticleEditBean> articleEditBeanMap = new HashMap<>();
}

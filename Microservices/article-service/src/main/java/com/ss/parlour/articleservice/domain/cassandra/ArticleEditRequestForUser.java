package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("articleeditrequestforuser")
@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequestForUser {

    @PrimaryKey
    private String userId;
    private HashMap<String, ArticleEditBean> articleEditBeanMapForUser = new HashMap<>();

}

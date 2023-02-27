package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@NoArgsConstructor
@UserDefinedType
@Table("sharedarticlebean")
public class SharedArticleBean {

    private String articleId;
    private String status;
    private String author;
}
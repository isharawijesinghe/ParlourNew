package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table("edit_draft_articles")
@Getter
@Setter
@NoArgsConstructor
public class EditDraftArticles {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String editor;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    private String authorName;
    private String title;
    private String summary;
    private String content;
    private String categoryId;
    private String thumbnailUrl;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String createdDate;

}

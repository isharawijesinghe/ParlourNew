package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table("edit_draft_articles")
@Getter
@Setter
@NoArgsConstructor
public class EditDraftArticles {

    @PrimaryKey
    private String articleId;
    private HashMap<String, Article> draftArticles = new HashMap<>();
}

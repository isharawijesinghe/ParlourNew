package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.List;

@Table("comment_by_article")
@Getter
@Setter
@NoArgsConstructor
public class CommentByArticle {

    @PrimaryKey
    private String articleId;
    private HashMap<String, List<Comment>> comments = new HashMap<>();

}

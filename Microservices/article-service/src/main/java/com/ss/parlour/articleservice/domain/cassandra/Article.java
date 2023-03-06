package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.*;

@Table("article")
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @PrimaryKey
    private String id;
    private String userName;
    private String title;
    private String summary;
    private String content;
    private String status;
    private String categoryId;
    private String thumbnailUrl;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Set<String> likedList = new HashSet<>();
    private Set<String> unLikedList = new HashSet<>();

}

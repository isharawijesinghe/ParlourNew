package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Timestamp;
import java.util.*;

@Table("comment")
@UserDefinedType
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @PrimaryKey
    private String id;
    private String articleId;
    private String parentId;
    private String userName;
    private String content;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Set<String> likedList = new HashSet<>();
    private Set<String> unLikedList = new HashSet<>();
    @org.springframework.data.annotation.Transient
    private List<Comment> subComments = new ArrayList<>();

}

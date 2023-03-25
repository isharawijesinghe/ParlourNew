package com.ss.parlour.articleservice.utils.bean;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.CommentGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddHelperBean {

    private Comment comment;
    private CommentGroup commentGroup;
}

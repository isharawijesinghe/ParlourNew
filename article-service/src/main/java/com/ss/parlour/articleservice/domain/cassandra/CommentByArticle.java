package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("commentbyarticle")
public class CommentByArticle {

    @PrimaryKey
    private String articleId;
    private List<Comment> comments = new ArrayList();

    public String getArticleId() {return articleId;}

    public void setArticleId(String articleId) {this.articleId = articleId;}

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

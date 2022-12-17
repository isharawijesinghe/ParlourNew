package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table("commentbyarticle")
public class CommentByArticle {

    @PrimaryKey
    private String articleId;
    private HashMap<String, ArrayList<Comment>> comments = new HashMap<>();

    public String getArticleId() {return articleId;}

    public void setArticleId(String articleId) {this.articleId = articleId;}

    public HashMap<String, ArrayList<Comment>> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, ArrayList<Comment>> comments) {
        this.comments = comments;
    }
}

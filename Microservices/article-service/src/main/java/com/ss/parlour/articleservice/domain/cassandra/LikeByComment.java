package com.ss.parlour.articleservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("likebycomment")
public class LikeByComment {

    @PrimaryKey
    private String commentId;
    private String articleId;
    private HashMap<String, Like> likeMap = new HashMap<>();

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public HashMap<String, Like> getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(HashMap<String, Like> likeMap) {
        this.likeMap = likeMap;
    }
}

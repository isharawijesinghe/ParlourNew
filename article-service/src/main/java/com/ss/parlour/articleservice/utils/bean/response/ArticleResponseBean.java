package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.domain.cassandra.Comment;

import java.util.ArrayList;
import java.util.List;

public class ArticleResponseBean {

    private List<Comment> articleComments = new ArrayList<>();

    public List<Comment> getArticleComments() {
        return articleComments;
    }

    public void setArticleComments(List<Comment> articleComments) {
        this.articleComments = articleComments;
    }
}

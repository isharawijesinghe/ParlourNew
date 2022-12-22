package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.CommentByArticle;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentByArticleRepositoryI extends CassandraRepository<CommentByArticle, String> {

    @Query("update commentbyarticle SET comments = ?0 WHERE articleId = ?1;")
    Object updateCommentListByArticle(List<Comment> comments, String articleId);
}

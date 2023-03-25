package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.CommentGroup;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentGroupRepositoryI extends CassandraRepository<CommentGroup, String> {

    @Query("update commentbyarticle SET comments = ?0 WHERE articleId = ?1;")
    Object updateCommentListByArticle(List<Comment> comments, String articleId);

    Optional<List<CommentGroup>> findByArticleIdAndParentId(String articleId, String parentId);

    Optional<CommentGroup> findByArticleIdAndParentIdAndCreatedDate(String articleId, String parentId, Timestamp createdDate);
}

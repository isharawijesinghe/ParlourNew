package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import jnr.x86asm.OP;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepositoryI extends CassandraRepository<Comment, String> {

    Optional<Comment> findByCommentIdAndArticleIdAndParentId(String commentId, String articleId, String parentId);
    Optional<Comment> findByCommentIdAndArticleId(String commentId, String articleId);
}

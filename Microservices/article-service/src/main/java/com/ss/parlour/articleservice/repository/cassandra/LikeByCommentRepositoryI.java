package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeByCommentRepositoryI extends CassandraRepository<LikeByComment, String> {

    Optional<LikeByComment> findLikeByAndCommentIdAndArticleId(String commentId, String articleId);
}

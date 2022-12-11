package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface LikeByCommentRepositoryI extends CassandraRepository<LikeByComment, String> {

    Optional<LikeByComment> findLikeByCommentByAndCommentIdAndArticleId(String commentId, String articleId);
}

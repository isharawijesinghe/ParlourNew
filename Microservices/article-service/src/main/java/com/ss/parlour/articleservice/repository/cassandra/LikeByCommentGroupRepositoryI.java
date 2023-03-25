package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticleGroup;
import com.ss.parlour.articleservice.domain.cassandra.LikeByCommentGroup;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface LikeByCommentGroupRepositoryI  extends CassandraRepository<LikeByCommentGroup, String> {

        Optional<LikeByCommentGroup> findByCommentIdAndArticleIdAndUserIdAndCreatedDate
            (String commentId, String articleId, String userId, Timestamp createdDate);
}

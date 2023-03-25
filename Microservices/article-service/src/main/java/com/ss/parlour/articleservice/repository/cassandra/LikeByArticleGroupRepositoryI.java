package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticleGroup;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface LikeByArticleGroupRepositoryI  extends CassandraRepository<LikeByArticleGroup, String> {

    Optional<LikeByArticleGroup> findByArticleIdAndUserIdAndCreatedDate(String articleId, String userId, Timestamp createdDate);
}

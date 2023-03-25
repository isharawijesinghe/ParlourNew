package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeByArticleRepositoryI extends CassandraRepository<LikeByArticle, String> {

    Optional<LikeByArticle> findByArticleIdAndUserId(String articleId, String userId);
}

package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.SharedArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface SharedArticlesRepositoryI extends CassandraRepository<SharedArticles, String> {

    Optional<SharedArticles> findSharedArticlesByEditRequestIdAndAndArticleId(String editRequestId, String articleId);
}

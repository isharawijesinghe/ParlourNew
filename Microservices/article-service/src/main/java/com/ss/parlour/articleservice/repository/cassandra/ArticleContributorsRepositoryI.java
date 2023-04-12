package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleContributors;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleContributorsRepositoryI extends CassandraRepository<ArticleContributors, String> {
    Optional<List<ArticleContributors>> findArticleContributorsByArticleId(String articleId);
}

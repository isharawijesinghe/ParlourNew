package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.ArticleContributors;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ArticleContributorsRepositoryI extends CassandraRepository<ArticleContributors, String> {
}

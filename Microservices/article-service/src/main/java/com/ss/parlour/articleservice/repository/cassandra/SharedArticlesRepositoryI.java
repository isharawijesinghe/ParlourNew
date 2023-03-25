package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.SharedArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface SharedArticlesRepositoryI extends CassandraRepository<SharedArticles, String> {
}

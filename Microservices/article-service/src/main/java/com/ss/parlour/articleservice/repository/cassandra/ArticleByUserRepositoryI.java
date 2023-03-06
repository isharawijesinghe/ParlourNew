package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ArticleByUserRepositoryI extends CassandraRepository<ArticleByUser, String> {
}

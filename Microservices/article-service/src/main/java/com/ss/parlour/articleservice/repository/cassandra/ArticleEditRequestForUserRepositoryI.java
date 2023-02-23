package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleEditRequestForUser;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEditRequestForUserRepositoryI extends CassandraRepository<ArticleEditRequestForUser, String> {
}

package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.SharedArticlesWithUser;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedArticlesWithUserRepositoryI extends CassandraRepository<SharedArticlesWithUser, String> {
}

package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.EditDraftArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EditDraftArticlesRepositoryI extends CassandraRepository<EditDraftArticles, String> {
}

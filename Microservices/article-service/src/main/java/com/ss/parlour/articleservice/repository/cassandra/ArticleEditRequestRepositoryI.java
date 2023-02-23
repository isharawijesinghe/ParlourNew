package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleEditRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEditRequestRepositoryI extends CassandraRepository<ArticleEditRequest, String> {
}

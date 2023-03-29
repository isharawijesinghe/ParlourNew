package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleContributors;
import com.ss.parlour.articleservice.domain.cassandra.EditArticleContributors;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EditArticleContributorsRepositoryI extends CassandraRepository<EditArticleContributors, String> {
}

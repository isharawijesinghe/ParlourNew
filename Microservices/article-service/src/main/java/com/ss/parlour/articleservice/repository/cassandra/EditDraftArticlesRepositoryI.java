package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.EditDraftArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface EditDraftArticlesRepositoryI extends CassandraRepository<EditDraftArticles, String> {

    Optional<EditDraftArticles> findEditDraftArticlesByEditRequestIdAndAndArticleId(String editRequestId, String articleId);
}

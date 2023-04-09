package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.ArticleByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleByUserRepositoryI extends CassandraRepository<ArticleByUser, String> {

    Optional<List<ArticleByUser>> findByUserId(String userId);
    Optional<ArticleByUser> findByUserIdAndArticleId(String userId, String articleId);
}

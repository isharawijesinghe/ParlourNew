package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.SharedArticlesWithUser;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharedArticlesWithUserRepositoryI extends CassandraRepository<SharedArticlesWithUser, String> {

    Optional<List<SharedArticlesWithUser>> findByRequesterId(String userId);
    Optional<SharedArticlesWithUser> findByRequesterIdAndArticleId(String userId, String articleId);
}

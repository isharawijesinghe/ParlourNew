package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.domain.cassandra.LikeByComment;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface LikeByArticleRepositoryI extends CassandraRepository<LikeByArticle, String> {

    Optional<LikeByArticle> findByArticleId(String articleId);
}

package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ArticleMappedStreamRepositoryI extends CassandraRepository<ArticleMappedStream, String> {

    Optional<List<ArticleMappedStream>> findByArticleId(String articleId);
    Optional<ArticleMappedStream> findByArticleIdAndStreamIdAndCreatedDate(String articleId, String streamId, Timestamp createdDate);
}

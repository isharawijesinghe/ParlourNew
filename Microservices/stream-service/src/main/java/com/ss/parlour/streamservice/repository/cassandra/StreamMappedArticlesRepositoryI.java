package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface StreamMappedArticlesRepositoryI extends CassandraRepository<StreamMappedArticles, String> {

    Optional<StreamMappedArticles> findByStreamIdAndArticleIdAndCreatedDate(String streamId, String articleId, Timestamp createdDate);
    Optional<List<StreamMappedArticles>> findByStreamId(String streamId);
}

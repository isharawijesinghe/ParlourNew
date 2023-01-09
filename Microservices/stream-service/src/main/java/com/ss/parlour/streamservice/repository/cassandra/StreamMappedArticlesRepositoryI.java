package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface StreamMappedArticlesRepositoryI extends CassandraRepository<StreamMappedArticles, String> {

    Optional<StreamMappedArticles> findByStreamIdAndAndUserName(String streamId, String userName);
    Optional<StreamMappedArticles> findByStreamId(String streamId);
}

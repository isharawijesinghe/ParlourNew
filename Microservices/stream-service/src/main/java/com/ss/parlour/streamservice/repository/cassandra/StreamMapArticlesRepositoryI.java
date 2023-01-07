package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.StreamMapArticles;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface StreamMapArticlesRepositoryI extends CassandraRepository<StreamMapArticles, String> {

    Optional<StreamMapArticles> findByStreamNameAndAndUserName(String streamName, String userName);
}

package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface StreamByUserRepositoryI extends CassandraRepository<StreamByUser, String> {

    Optional<List<StreamByUser>> findByUserId(String userId);
    Optional<StreamByUser> findByUserIdAndStreamIdAndCreatedDate(String userId, String streamId, Timestamp createdDate);
}

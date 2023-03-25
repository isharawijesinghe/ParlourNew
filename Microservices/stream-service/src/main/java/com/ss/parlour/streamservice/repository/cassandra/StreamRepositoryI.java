package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface StreamRepositoryI extends CassandraRepository<Stream, String> {

    Optional<Stream> findByStreamId(String streamId);
}

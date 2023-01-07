package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface StreamRepositoryI extends CassandraRepository<Stream, String> {
}

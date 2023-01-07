package com.ss.parlour.streamservice.repository.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserMappedStreamRepositoryI extends CassandraRepository<UserMappedStream, String> {

    Optional<UserMappedStream> findUserMappedStreamByUserName(String userName);
}

package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserLoginEmailMapper;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserLoginEmailMapperRepositoryI extends CassandraRepository<UserLoginEmailMapper, String> {

    Optional<UserLoginEmailMapper> findByEmail(String email);
}

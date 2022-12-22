package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface LoginNameEmailMapperRepositoryI extends CassandraRepository<UserLoginNameEmailMapper, String> {

    Optional<UserLoginNameEmailMapper> findByEmail(String email);
}

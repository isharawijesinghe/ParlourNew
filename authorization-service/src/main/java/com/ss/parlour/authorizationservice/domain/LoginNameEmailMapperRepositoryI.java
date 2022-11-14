package com.ss.parlour.authorizationservice.domain;

import com.ss.parlour.authorizationservice.repository.UserLoginNameEmailMapper;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface LoginNameEmailMapperRepositoryI extends CassandraRepository<UserLoginNameEmailMapper, String> {

    Optional<UserLoginNameEmailMapper> findByEmail(String email);
}

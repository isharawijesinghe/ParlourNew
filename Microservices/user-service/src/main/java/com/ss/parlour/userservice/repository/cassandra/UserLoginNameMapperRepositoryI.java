package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserLoginNameMapper;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserLoginNameMapperRepositoryI extends CassandraRepository<UserLoginNameMapper, String> {

    Optional<UserLoginNameMapper> findByLoginName(String loginName);
}

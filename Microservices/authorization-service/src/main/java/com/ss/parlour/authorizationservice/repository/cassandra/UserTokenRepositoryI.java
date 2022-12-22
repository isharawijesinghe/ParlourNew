package com.ss.parlour.authorizationservice.repository.cassandra;

import com.ss.parlour.authorizationservice.domain.cassandra.UserToken;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserTokenRepositoryI extends CassandraRepository<UserToken, String> {

    Optional<UserToken> findByTokenAndType(String token, String type);
}

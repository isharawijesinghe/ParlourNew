package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserToken;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserTokenRepositoryI extends CassandraRepository<UserToken, String> {

    Optional<UserToken> findByUserNameAndActionType(String userName, String actionType);
}

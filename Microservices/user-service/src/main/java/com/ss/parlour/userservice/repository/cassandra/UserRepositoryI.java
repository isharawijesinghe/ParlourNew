package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryI extends CassandraRepository<User, String> {

    Optional<User> findByLoginName(String loginName);
}

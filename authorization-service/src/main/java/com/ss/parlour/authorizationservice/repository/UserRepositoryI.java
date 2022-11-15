package com.ss.parlour.authorizationservice.repository;

import com.ss.parlour.authorizationservice.domain.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UserRepositoryI extends CassandraRepository<User, String> {

    Optional<User> findByLoginName(String loginName);
}

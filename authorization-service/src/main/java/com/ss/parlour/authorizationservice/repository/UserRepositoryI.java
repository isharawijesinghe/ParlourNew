package com.ss.parlour.authorizationservice.repository;

import com.ss.parlour.authorizationservice.domain.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryI extends CassandraRepository<User, String> {

    User findByLoginName(String loginName);
}
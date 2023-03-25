package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface UserInterestsRepositoryI extends CassandraRepository<UserInterests, String> {

    Optional<List<UserInterests>> findByUserId(String userId);
}

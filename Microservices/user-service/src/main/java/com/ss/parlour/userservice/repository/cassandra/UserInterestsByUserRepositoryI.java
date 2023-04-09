package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import com.ss.parlour.userservice.domain.cassandra.UserInterestsByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface UserInterestsByUserRepositoryI extends CassandraRepository<UserInterestsByUser, String> {
    Optional<List<UserInterestsByUser>> findByUserId(String userId);
}

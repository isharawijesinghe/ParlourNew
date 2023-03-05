package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserInterestsRepositoryI extends CassandraRepository<UserInterests, String> {
}

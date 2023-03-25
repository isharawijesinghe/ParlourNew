package com.ss.parlour.userservice.repository.cassandra;

import com.ss.parlour.userservice.domain.cassandra.UserInfo;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface UserInfoRepositoryI extends CassandraRepository<UserInfo, String> {

    Optional<UserInfo> findUserInfoByUserId(String userId);
}

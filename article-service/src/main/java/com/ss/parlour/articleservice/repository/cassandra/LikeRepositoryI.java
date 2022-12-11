package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Like;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface LikeRepositoryI extends CassandraRepository<Like, String> {

    Optional<Like> findByKey(String key);
}

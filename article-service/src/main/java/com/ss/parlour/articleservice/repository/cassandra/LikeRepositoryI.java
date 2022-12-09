package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Like;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface LikeRepositoryI extends CassandraRepository<Like, String> {
}

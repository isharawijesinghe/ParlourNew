package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CommentRepositoryI extends CassandraRepository<Comment, String> {
}

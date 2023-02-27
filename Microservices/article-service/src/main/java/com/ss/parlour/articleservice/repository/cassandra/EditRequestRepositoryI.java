package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.EditRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface EditRequestRepositoryI extends CassandraRepository<EditRequest, String> {

    Optional<EditRequest> findEditRequestByEditRequestId(String articleId);
}

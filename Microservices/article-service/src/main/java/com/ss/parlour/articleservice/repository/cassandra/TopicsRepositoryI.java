package com.ss.parlour.articleservice.repository.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Topics;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicsRepositoryI extends CassandraRepository<Topics, String> {

    @Query("select * from topics;")
    Optional<List<Topics>> loadAllTopicsEntries();
}

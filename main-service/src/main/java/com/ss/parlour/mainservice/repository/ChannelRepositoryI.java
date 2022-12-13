package com.ss.parlour.mainservice.repository;

import com.ss.parlour.mainservice.domain.Channel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepositoryI extends CassandraRepository<Channel, Integer>{
    public Channel findByChannelId(Integer id);
}

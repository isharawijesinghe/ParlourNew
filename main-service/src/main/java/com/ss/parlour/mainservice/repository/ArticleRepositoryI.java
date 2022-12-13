package com.ss.parlour.mainservice.repository;

import com.ss.parlour.mainservice.domain.Article;
import com.ss.parlour.mainservice.domain.Channel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepositoryI extends CassandraRepository<Article, String> {
    public List<Article> findByChannelId(int channelID);
}

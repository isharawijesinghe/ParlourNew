package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMapArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;

import java.util.Optional;

public interface StreamDAOI {

    void saveStream(Stream stream);
    void saveStreamMapArticle(StreamMapArticles streamMapArticles);
    Optional<StreamMapArticles> findByStreamNameAndAndUserName(String streamName, String userName);
    Optional<UserMappedStream> findUserMappedStreamByUserName(String userName);
}

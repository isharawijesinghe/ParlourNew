package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;

import java.util.Optional;

public interface StreamDAOI {

    void saveStream(Stream stream);
    void saveStreamMapArticle(StreamMappedArticles streamMappedArticles);
    Optional<StreamMappedArticles> findByStreamIdAndAndUserName(String streamName, String userName);
    Optional<UserMappedStream> findUserMappedStreamByUserName(String userName);
    Optional<Stream> findStreamById(String streamId);
    Optional<StreamMappedArticles> findByStreamId(String streamId);
    void deleteStreamByStreamId(String streamId);
    void deleteStreamMappedArticlesByStreamId(String streamId);
    void deleteUserMappedStreamByUserId(String userId);
}

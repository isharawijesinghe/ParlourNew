package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;
import com.ss.parlour.streamservice.repository.cassandra.StreamMappedArticlesRepositoryI;
import com.ss.parlour.streamservice.repository.cassandra.StreamRepositoryI;
import com.ss.parlour.streamservice.repository.cassandra.UserMappedStreamRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StreamDAO implements StreamDAOI{

    @Autowired
    private StreamRepositoryI streamRepositoryI;

    @Autowired
    private StreamMappedArticlesRepositoryI streamMapArticlesRepositoryI;

    @Autowired
    private UserMappedStreamRepositoryI userMappedStreamRepositoryI;

    @Override
    public void saveStream(Stream stream){
        streamRepositoryI.save(stream);
    }


    @Override
    public void saveStreamMapArticle(StreamMappedArticles streamMappedArticles){
        streamMapArticlesRepositoryI.save(streamMappedArticles);
    }

    @Override
    public Optional<StreamMappedArticles> findByStreamIdAndAndUserName(String streamId, String userName){
        return streamMapArticlesRepositoryI.findByStreamIdAndAndUserName(streamId, userName);
    }

    @Override
    public Optional<StreamMappedArticles> findByStreamId(String streamId){
        return streamMapArticlesRepositoryI.findByStreamId(streamId);
    }

    @Override
    public Optional<UserMappedStream> findUserMappedStreamByUserName(String userName){
        return userMappedStreamRepositoryI.findUserMappedStreamByUserName(userName);
    }

    @Override
    public Optional<Stream> findStreamById(String streamId){
        return streamRepositoryI.findById(streamId);
    }

    @Override
    public void deleteStreamByStreamId(String streamId){
        streamRepositoryI.deleteById(streamId);
    }

    @Override
    public void deleteStreamMappedArticlesByStreamId(String streamId){
        streamMapArticlesRepositoryI.deleteById(streamId);
    }

    @Override
    public void deleteUserMappedStreamByUserId(String userId){
        userMappedStreamRepositoryI.deleteById(userId);
    }


}

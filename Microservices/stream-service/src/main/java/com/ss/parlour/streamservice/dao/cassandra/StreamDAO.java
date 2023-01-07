package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMapArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;
import com.ss.parlour.streamservice.repository.cassandra.StreamMapArticlesRepositoryI;
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
    private StreamMapArticlesRepositoryI streamMapArticlesRepositoryI;

    @Autowired
    private UserMappedStreamRepositoryI userMappedStreamRepositoryI;

    @Override
    public void saveStream(Stream stream){
        streamRepositoryI.save(stream);
    }


    @Override
    public void saveStreamMapArticle(StreamMapArticles streamMapArticles){
        streamMapArticlesRepositoryI.save(streamMapArticles);
    }

    @Override
    public Optional<StreamMapArticles> findByStreamNameAndAndUserName(String streamName, String userName){
        return streamMapArticlesRepositoryI.findByStreamNameAndAndUserName(streamName, userName);
    }

    @Override
    public Optional<UserMappedStream> findUserMappedStreamByUserName(String userName){
        return userMappedStreamRepositoryI.findUserMappedStreamByUserName(userName);
    }
}

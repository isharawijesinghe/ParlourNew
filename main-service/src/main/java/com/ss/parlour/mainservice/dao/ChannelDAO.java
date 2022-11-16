package com.ss.parlour.mainservice.dao;

import com.ss.parlour.mainservice.domain.Channel;
import com.ss.parlour.mainservice.repository.cassandra.ChannelRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelDAO implements ChannelDAOI{

    @Autowired
    ChannelRepositoryI channelRepositoryI;

    public Channel findByChannelID(Integer id){
        return channelRepositoryI.findByChannelID(id);
    }
}

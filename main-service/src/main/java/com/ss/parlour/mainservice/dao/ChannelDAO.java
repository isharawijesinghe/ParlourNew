package com.ss.parlour.mainservice.dao;

import com.ss.parlour.mainservice.domain.cassandra.Channel;
import com.ss.parlour.mainservice.repository.cassandra.ChannelRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChannelDAO implements ChannelDAOI{

    @Autowired
    ChannelRepositoryI channelRepositoryI;

    public Channel findByChannelID(Integer id){
        return channelRepositoryI.findByChannelID(id);
    }

    public Channel insertChannel(Channel channel){
        return channelRepositoryI.insert(channel);
    }

    public void deleteChannel(Channel channel){
        channelRepositoryI.delete(channel);
    }

    public List<Channel> findAll(){
        return channelRepositoryI.findAll();
    }
}

package com.ss.parlour.mainservice.dao;

import com.ss.parlour.mainservice.domain.cassandra.Channel;

import java.util.List;

public interface ChannelDAOI {

    Channel findByChannelID(Integer id);
    Channel insertChannel(Channel channel);
    void deleteChannel(Channel channel);
    List<Channel> findAll();
}

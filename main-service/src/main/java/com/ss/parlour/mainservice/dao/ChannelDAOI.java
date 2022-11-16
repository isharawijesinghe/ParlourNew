package com.ss.parlour.mainservice.dao;

import com.ss.parlour.mainservice.domain.Channel;

public interface ChannelDAOI {

    Channel findByChannelID(Integer id);
}

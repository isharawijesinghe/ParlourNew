package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;

import java.util.List;

public interface ChannelServiceI {
    ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean addToChannel(ArticleRequestBean article);
    ChannelResponseBean addToChannel(ArticleRequestBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean);
}

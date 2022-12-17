package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;

public interface ChannelServiceI {
    ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean addToChannel(ArticleRequestBean article);
    ChannelResponseBean addToChannel(ArticleRequestBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean);
}

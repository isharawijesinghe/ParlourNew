package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;

public interface ChannelServiceHandlerI {
    ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean addToChannel(ArticleBean article);
    ChannelResponseBean addToChannel(ArticleBean[] articles);
    ChannelResponseBean[] viewChannel();
    ChannelResponseBean[] viewChannel(ChannelRequestBean channelRequestBean);
    ArticleBean[] viewArticles(ChannelRequestBean channelRequestBean);
}

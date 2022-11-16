package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.utils.bean.ArticleBean;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;

import java.util.List;

public interface ChannelServiceHandlerI {
    ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean addToChannel(ArticleBean article);
    ChannelResponseBean addToChannel(ArticleBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean);
    List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean);
}

package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;

import java.util.List;

public interface ChannelServiceI {
    ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean);
    ChannelResponseBean addToChannel(ArticleBean article);
    ChannelResponseBean addToChannel(ArticleBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean);
    List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean);
}

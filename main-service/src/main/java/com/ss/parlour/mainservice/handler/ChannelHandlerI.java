package com.ss.parlour.mainservice.handler;


import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.domain.Channel;

import java.util.List;

public interface ChannelHandlerI {
    ChannelResponseBean createChannel(ChannelRequestBean channel);
    ChannelResponseBean deleteChannel(ChannelRequestBean channel);
    ChannelResponseBean addToChannel(ArticleBean article);
    ChannelResponseBean addToChannel(ArticleBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(int id);
    List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean);

}

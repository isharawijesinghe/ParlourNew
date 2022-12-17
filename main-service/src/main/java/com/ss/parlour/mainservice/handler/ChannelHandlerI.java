package com.ss.parlour.mainservice.handler;


import com.ss.parlour.mainservice.utils.bean.ArticleBean;
import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;

import java.util.List;

public interface ChannelHandlerI {
    ChannelResponseBean createChannel(ChannelRequestBean channel);
    ChannelResponseBean deleteChannel(ChannelRequestBean channel);
    ChannelResponseBean addToChannel(ArticleRequestBean article);
    ChannelResponseBean addToChannel(ArticleRequestBean[] articles);
    ChannelResponseBean viewChannels();
    ChannelResponseBean viewChannel(int id);
    List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean);

}

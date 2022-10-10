package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceHandler implements ChannelServiceHandlerI{
    @Override
    public ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean) {
        return null;
    }

    @Override
    public ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean) {
        return null;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean article) {
        return null;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean[] articles) {
        return null;
    }

    @Override
    public ChannelResponseBean[] viewChannel() {
        return new ChannelResponseBean[0];
    }

    @Override
    public ChannelResponseBean[] viewChannel(ChannelRequestBean channelRequestBean) {
        return new ChannelResponseBean[0];
    }

    @Override
    public ArticleBean[] viewArticles(ChannelRequestBean channelRequestBean) {
        return new ArticleBean[0];
    }
}

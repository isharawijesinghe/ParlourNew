package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.handler.ChannelHandlerI;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService implements ChannelServiceI {

    @Autowired
    private ChannelHandlerI channelHandlerI;

    @Override
    public ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean) {
        return channelHandlerI.createChannel(channelRequestBean);
    }

    @Override
    public ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean) {
        return channelHandlerI.deleteChannel(channelRequestBean);
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleRequestBean article) {
        return channelHandlerI.addToChannel(article);
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleRequestBean[] articles) {
        return channelHandlerI.addToChannel(articles);
    }

    @Override
    public ChannelResponseBean viewChannels() {
        return channelHandlerI.viewChannels();
    }

    @Override
    public ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean) {
        return channelHandlerI.viewChannel(channelRequestBean.getId());
    }

    public ChannelHandlerI getChannelHandlerI() {
        return channelHandlerI;
    }

    public void setChannelHandlerI(ChannelHandlerI channelHandlerI) {
        this.channelHandlerI = channelHandlerI;
    }
}

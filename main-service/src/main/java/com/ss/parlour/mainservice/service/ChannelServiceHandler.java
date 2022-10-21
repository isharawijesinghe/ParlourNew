package com.ss.parlour.mainservice.service;

import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.handler.ChannelHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceHandler implements ChannelServiceHandlerI {

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
    public ChannelResponseBean addToChannel(ArticleBean article) {
        return channelHandlerI.addToChannel(article);
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean[] articles) {
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

    @Override
    public List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean) {
        return channelHandlerI.viewArticles(channelRequestBean);
    }

    public ChannelHandlerI getChannelHandlerI() {
        return channelHandlerI;
    }

    public void setChannelHandlerI(ChannelHandlerI channelHandlerI) {
        this.channelHandlerI = channelHandlerI;
    }
}

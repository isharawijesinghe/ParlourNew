package com.ss.parlour.mainservice.handler;

import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.bean.Const;
import com.ss.parlour.mainservice.domain.Channel;
import com.ss.parlour.mainservice.exception.MainServiceRuntimeException;
import com.ss.parlour.mainservice.repository.ChannelRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChannelHandler implements ChannelHandlerI {
    @Autowired
    private ChannelRepositoryI channelRepositoryI;

    @Override
    public ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean) {
        Channel channel=new Channel();
        channel.setName(channelRequestBean.getChannelName());
        if(channelRequestBean.getChannelName()==null || channelRequestBean.getChannelName().trim().length()==0){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_NAME);
        }
        channel= channelRepositoryI.insert(channel); //todo set auto generated ID
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean) {
        if(channelRequestBean.getId()<=0){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel=new Channel();
        channel.setChannelID(channelRequestBean.getId());
        channelRepositoryI.delete(channel);
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean article) {
        if(article.getChannelID()<=0){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel =channelRepositoryI.findByChannelID(article.getChannelID());
        if(channel==null){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_ID);
        }
        if(channel.getArticles()==null){
            channel.setArticles(new ArrayList<>());
        }
        channel.getArticles().add(article);
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean[] articles) {
        return  new ChannelResponseBean();//  channelRepositoryI.addToChannel(articles);//todo
    }

    @Override
    public ChannelResponseBean viewChannels() {
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setChannels(channelRepositoryI.findAll());
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean viewChannel(int id) {
        Channel channel=  channelRepositoryI.findByChannelID(id);
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean) {
        if(channelRequestBean.getId()<=0){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel=channelRepositoryI.findByChannelID(channelRequestBean.getId());
        return channel.getArticles();
    }

    public ChannelRepositoryI getChannelRepositoryI() {
        return channelRepositoryI;
    }

    public void setChannelRepositoryI(ChannelRepositoryI channelRepositoryI) {
        this.channelRepositoryI = channelRepositoryI;
    }
}

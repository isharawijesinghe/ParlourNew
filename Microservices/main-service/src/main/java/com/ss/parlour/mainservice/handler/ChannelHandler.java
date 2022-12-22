package com.ss.parlour.mainservice.handler;

import com.ss.parlour.mainservice.dao.ChannelDAOI;
import com.ss.parlour.mainservice.utils.bean.*;
import com.ss.parlour.mainservice.domain.cassandra.Channel;
import com.ss.parlour.mainservice.utils.exception.MainServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChannelHandler implements ChannelHandlerI {

    @Autowired
    private ChannelDAOI channelDAOI;

    @Override
    public ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean) {
        Channel channel=new Channel();
        channel.setName(channelRequestBean.getChannelName());
        if(channelRequestBean.getChannelName() == null || channelRequestBean.getChannelName().trim().length()==0){
            throw new MainServiceRuntimeException(MainConst.ERROR_DES_INVALID_CHANEL_NAME);
        }
        channel = channelDAOI.insertChannel(channel); //todo set auto generated ID
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean) {
        if(channelRequestBean.getId() <= 0){
            throw new MainServiceRuntimeException(MainConst.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel = new Channel();
        channel.setChannelID(channelRequestBean.getId());
        channelDAOI.deleteChannel(channel);
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleRequestBean article) {
        if(article.getChannelID()<=0){
            throw new MainServiceRuntimeException(MainConst.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel = channelDAOI.findByChannelID(article.getChannelID());
        if(channel==null){
            throw new MainServiceRuntimeException(MainConst.ERROR_DES_INVALID_CHANEL_ID);
        }
        if(channel.getArticles()==null){
            channel.setArticles(new ArrayList<>());
        }
        //channel.getArticles().add(article);
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleRequestBean[] articles) {
        return  new ChannelResponseBean();//  channelRepositoryI.addToChannel(articles);//todo
    }

    @Override
    public ChannelResponseBean viewChannels() {
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setChannels(channelDAOI.findAll());
        return channelResponseBean;
    }

    @Override
    public ChannelResponseBean viewChannel(int id) {
        Channel channel=  channelDAOI.findByChannelID(id);
        ChannelResponseBean channelResponseBean= new ChannelResponseBean();
        channelResponseBean.setId(channel.getChannelID());
        channelResponseBean.setChannel(channel);
        return channelResponseBean;
    }

    @Override
    public List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean) {
        if(channelRequestBean.getId()<=0){
            throw new MainServiceRuntimeException(MainConst.ERROR_DES_INVALID_CHANEL_ID);
        }
        Channel channel = channelDAOI.findByChannelID(channelRequestBean.getId());
        return channel.getArticles();
    }
}

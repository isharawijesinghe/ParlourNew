package com.ss.parlour.mainservice.service;

import com.kenai.jffi.Main;
import com.ss.parlour.mainservice.utils.bean.ArticleBean;
import com.ss.parlour.mainservice.utils.bean.ChannelErrorCodes;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.handler.ChannelHandlerI;
import com.ss.parlour.mainservice.utils.exception.MainServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceHandler implements ChannelServiceHandlerI {

    @Autowired
    private ChannelHandlerI channelHandlerI;

    @Override
    public ChannelResponseBean createChannel(ChannelRequestBean channelRequestBean) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.createChannel(channelRequestBean);
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ChannelResponseBean deleteChannel(ChannelRequestBean channelRequestBean) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.deleteChannel(channelRequestBean);
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean article) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.addToChannel(article);
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ChannelResponseBean addToChannel(ArticleBean[] articles) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.addToChannel(articles);
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ChannelResponseBean viewChannels() {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.viewChannels();
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ChannelResponseBean viewChannel(ChannelRequestBean channelRequestBean) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            channelResponseBean = channelHandlerI.viewChannel(channelRequestBean.getId());
            return channelResponseBean;
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public List<ArticleBean> viewArticles(ChannelRequestBean channelRequestBean) {
        ChannelResponseBean channelResponseBean = new ChannelResponseBean();
        try{
            return channelHandlerI.viewArticles(channelRequestBean);
        }catch (MainServiceRuntimeException ex){
            //todo add logger
            throw ex;
        }catch (Exception ex){
            //todo add logger
            throw new MainServiceRuntimeException(ChannelErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    public ChannelHandlerI getChannelHandlerI() {
        return channelHandlerI;
    }

    public void setChannelHandlerI(ChannelHandlerI channelHandlerI) {
        this.channelHandlerI = channelHandlerI;
    }
}

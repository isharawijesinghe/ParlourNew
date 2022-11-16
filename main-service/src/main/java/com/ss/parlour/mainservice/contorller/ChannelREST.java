package com.ss.parlour.mainservice.contorller;

import org.springframework.http.MediaType;
import com.ss.parlour.mainservice.utils.bean.ArticleBean;
import com.ss.parlour.mainservice.utils.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.utils.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.service.ChannelServiceHandlerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/channel",consumes= MediaType.APPLICATION_JSON_VALUE)
public class ChannelREST {
    private static Logger logger= LogManager.getLogger("public class ChannelREST {\n.class");

    @Autowired
    private ChannelServiceHandlerI channelServiceHandler;

    @RequestMapping(value = "/createChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> create(@RequestBody ChannelRequestBean request){
        logger.debug("=== Create channel request found: " + request);
        ChannelResponseBean channelResponseBean = channelServiceHandler.createChannel(request);
        return ResponseEntity.ok().body(channelResponseBean);
    }

    @RequestMapping(value = "/deleteChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteChannel(@RequestBody ChannelRequestBean request){
        logger.debug("=== Delete channel request found: " + request);
        ChannelResponseBean channelResponseBean =  channelServiceHandler.deleteChannel(request);
        return ResponseEntity.ok().body(channelResponseBean);
    }

    @RequestMapping(value = "/addArticleToChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addToChannel(@RequestBody ArticleBean articleBean){
        logger.debug("=== Add to channel request found : " + articleBean);
        ChannelResponseBean channelResponseBean =  channelServiceHandler.addToChannel(articleBean);
        return ResponseEntity.ok().body(channelResponseBean);
    }

    @RequestMapping(value = "/addArticlesToChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addToChannel(@RequestBody ArticleBean[] request){
        logger.debug("=== Add to channel(2) request found: " + request);
        ChannelResponseBean channelResponseBean =  channelServiceHandler.addToChannel(request);
        return ResponseEntity.ok().body(channelResponseBean);
    }

    @RequestMapping(value = "/viewChannels", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> viewChannels(){
        logger.debug("=== View channel request found: " );
        ChannelResponseBean channelResponseBean =  channelServiceHandler.viewChannels();
        return ResponseEntity.ok().body(channelResponseBean);
    }

    @RequestMapping(value = "/viewChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> viewChannel(@RequestBody ChannelRequestBean request){
        logger.debug("=== View channel request found:"+request);
        ChannelResponseBean channelResponseBean =  channelServiceHandler.viewChannel(request);
        return ResponseEntity.ok().body(channelResponseBean);
    }

  }


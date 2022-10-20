package com.ss.parlour.mainservice.contorller;

<<<<<<< Updated upstream
import org.springframework.http.MediaType;
=======
import com.ss.parlour.mainservice.bean.ArticleBean;
import com.ss.parlour.mainservice.bean.ChannelRequestBean;
import com.ss.parlour.mainservice.bean.ChannelResponseBean;
import com.ss.parlour.mainservice.service.ChannelService;
import com.ss.parlour.mainservice.service.ChannelServiceI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/channel",consumes= MediaType.APPLICATION_JSON_VALUE)
public class ChannelREST {
    private static Logger logger= LogManager.getLogger("public class ChannelREST {\n.class");
    @Autowired
    private ChannelServiceI channelServiceHandler;

    @RequestMapping(value = "/createChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean create(@RequestBody ChannelRequestBean request){
        logger.debug("=Create channel request found:"+request);
        return channelServiceHandler.createChannel(request);
    }

    @RequestMapping(value = "/deleteChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean deleteChannel(@RequestBody ChannelRequestBean request){
        logger.debug("=Delete channel request found:"+request);
        return channelServiceHandler.deleteChannel(request);
    }

    @RequestMapping(value = "/addArticleToChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean addToChannel(@RequestBody ArticleBean articleBean){
        logger.debug("=Add to channel(1) request found:"+articleBean);
        return channelServiceHandler.addToChannel(articleBean);
    }

    @RequestMapping(value = "/addArticlesToChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean addToChannel(@RequestBody ArticleBean[] request){
        logger.debug("=Add to channel(2) request found:"+request);
        return channelServiceHandler.addToChannel(request);
    }

    @RequestMapping(value = "/viewChannels", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean viewChannels(){
        logger.debug("=View channel request found:");
        return channelServiceHandler.viewChannels();
    }

    @RequestMapping(value = "/viewChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ChannelResponseBean viewChannel(@RequestBody ChannelRequestBean request){
        logger.debug("=View channel request found:"+request);
        return channelServiceHandler.viewChannel(request);
    }

  }


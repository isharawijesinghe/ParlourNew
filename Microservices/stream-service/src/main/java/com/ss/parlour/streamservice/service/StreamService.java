package com.ss.parlour.streamservice.service;

import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.handler.StreamHandlerI;
import com.ss.parlour.streamservice.utils.bean.StreamConst;
import com.ss.parlour.streamservice.utils.bean.StreamCreateHelperBean;
import com.ss.parlour.streamservice.utils.bean.StreamErrorCodes;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.*;
import com.ss.parlour.streamservice.utils.exception.StreamServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamService implements StreamServiceI{

    @Autowired
    private StreamHandlerI streamHandlerI;

    @Override
    public StreamCreateResponse createStream(StreamCreateRequest streamCreateRequest){
        StreamCreateResponse streamCommonResponse = new StreamCreateResponse();
        String streamId = streamHandlerI.createStream(streamCreateRequest);
        streamCommonResponse.setStreamId(streamId);
        streamCommonResponse.setStatus(StreamConst.STATUS_SUCCESS);
        streamCommonResponse.setNarration(StreamConst.STREAM_CREATE_SUCCESS);
        return streamCommonResponse;
    }

    @Override
    public StreamDeleteResponse deleteStream(StreamDeleteRequest streamDeleteRequest){
        StreamDeleteResponse streamDeleteResponse = new StreamDeleteResponse();
        streamHandlerI.deleteStream(streamDeleteRequest);
        streamDeleteResponse.setStreamId(streamDeleteRequest.getStreamId());
        streamDeleteResponse.setStatus(StreamConst.STATUS_SUCCESS);
        streamDeleteResponse.setNarration(StreamConst.STREAM_DELETE_SUCCESS);
        return streamDeleteResponse;
    }

    @Override
    public ArticleStreamAddResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest){
        ArticleStreamAddResponse articleStreamAddResponse = new ArticleStreamAddResponse();
        streamHandlerI.addArticleToStream(articleToStreamRequest);
        articleStreamAddResponse.setStreamId(articleToStreamRequest.getStreamId());
        articleStreamAddResponse.setStatus(StreamConst.STATUS_SUCCESS);
        articleStreamAddResponse.setNarration(StreamConst.ADD_ARTICLES_TO_STREAM_SUCCESS);
        return articleStreamAddResponse;
    }

    @Override
    public UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest){
        UserMappedStreamResponse userMappedStreamResponse = new UserMappedStreamResponse();
        List<StreamByUser> listOfStreamByUser = streamHandlerI.findStreamByUser(streamRequest);
        userMappedStreamResponse.setStreamByUser(listOfStreamByUser);
        return userMappedStreamResponse;
    }

    @Override
    public StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest){
        StreamMappedArticleResponse streamMappedArticleResponse = new StreamMappedArticleResponse();
        List<StreamMappedArticles> streamMappedArticlesList = streamHandlerI.findArticlesByStream(streamMappedArticleRequest);
        streamMappedArticleResponse.setStreamMappedArticles(streamMappedArticlesList);
        return streamMappedArticleResponse;
    }
}

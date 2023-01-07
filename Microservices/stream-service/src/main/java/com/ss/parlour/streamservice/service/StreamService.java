package com.ss.parlour.streamservice.service;

import com.ss.parlour.streamservice.handler.StreamHandlerI;
import com.ss.parlour.streamservice.utils.bean.StreamErrorCodes;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;
import com.ss.parlour.streamservice.utils.exception.StreamServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamService implements StreamServiceI{

    @Autowired
    private StreamHandlerI streamHandlerI;

    @Override
    public StreamCommonResponse createStream(StreamCreateRequest streamCreateRequest){
        try {
            return streamHandlerI.createStream(streamCreateRequest);
        }catch (StreamServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new StreamServiceRuntimeException(StreamErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public StreamCommonResponse deleteStream(StreamDeleteRequest streamDeleteRequest){
        try {
            return streamHandlerI.deleteStream(streamDeleteRequest);
        }catch (StreamServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new StreamServiceRuntimeException(StreamErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public StreamCommonResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest){
        try {
            return streamHandlerI.addArticleToStream(articleToStreamRequest);
        }catch (StreamServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new StreamServiceRuntimeException(StreamErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest){
        try {
            return streamHandlerI.findStreamByUser(streamRequest);
        }catch (StreamServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new StreamServiceRuntimeException(StreamErrorCodes.UNKNOWN_ERROR, ex);
        }
    }

    @Override
    public StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest){
        try {
            return streamHandlerI.findArticlesByStream(streamMappedArticleRequest);
        }catch (StreamServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new StreamServiceRuntimeException(StreamErrorCodes.UNKNOWN_ERROR, ex);
        }
    }
}

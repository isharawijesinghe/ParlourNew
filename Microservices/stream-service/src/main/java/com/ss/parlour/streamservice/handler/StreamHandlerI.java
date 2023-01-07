package com.ss.parlour.streamservice.handler;

import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;

public interface StreamHandlerI {
    StreamCommonResponse createStream(StreamCreateRequest streamCreateRequest);
    StreamCommonResponse deleteStream(StreamDeleteRequest streamDeleteRequest);
    StreamCommonResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest);
    UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest);
    StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest);
}

package com.ss.parlour.streamservice.service;

import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;

public interface StreamServiceI{

    StreamCommonResponse createStream(StreamCreateRequest streamCreateRequest);
    StreamCommonResponse deleteStream(StreamDeleteRequest streamDeleteRequest);
    StreamCommonResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest);
    UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest);
    StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest);
}

package com.ss.parlour.streamservice.service;

import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.*;

public interface StreamServiceI{

    StreamCreateResponse createStream(StreamCreateRequest streamCreateRequest);
    StreamDeleteResponse deleteStream(StreamDeleteRequest streamDeleteRequest);
    ArticleStreamAddResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest);
    UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest);
    StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest);
}

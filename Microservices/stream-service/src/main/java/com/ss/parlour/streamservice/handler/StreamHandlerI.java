package com.ss.parlour.streamservice.handler;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;

import java.util.List;

public interface StreamHandlerI {
    String createStream(StreamCreateRequest streamCreateRequest);
    void deleteStream(StreamDeleteRequest streamDeleteRequest);
    void addArticleToStream(ArticleToStreamRequest articleToStreamRequest);
    List<StreamByUser> findStreamByUser(StreamRequest streamRequest);
    List<StreamMappedArticles> findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest);
}

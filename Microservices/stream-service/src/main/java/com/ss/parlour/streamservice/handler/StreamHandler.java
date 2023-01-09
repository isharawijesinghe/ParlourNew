package com.ss.parlour.streamservice.handler;

import com.ss.parlour.streamservice.dao.cassandra.StreamDAOI;
import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;
import com.ss.parlour.streamservice.utils.bean.Article;
import com.ss.parlour.streamservice.utils.bean.StreamBean;
import com.ss.parlour.streamservice.utils.bean.StreamConst;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;
import com.ss.parlour.streamservice.utils.common.StreamKeyGenerator;
import com.ss.parlour.streamservice.utils.validator.StreamValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StreamHandler implements StreamHandlerI{

    @Autowired
    private StreamValidatorI streamValidatorI;

    @Autowired
    private StreamDAOI streamDAOI;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StreamKeyGenerator streamKeyGenerator;

    @Override
    public StreamCommonResponse createStream(StreamCreateRequest streamCreateRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        StreamBean streamBean = streamValidatorI.streamCreateRequestValidate(streamCreateRequest);
        if (streamCreateRequest.getStreamBean().getStreamId() == null || streamCreateRequest.getStreamBean().getStreamId().isEmpty()){
            streamCreateRequest.getStreamBean().setStreamId(streamKeyGenerator.streamKeyGenerator(streamBean.getUserName()));
        }
        Stream stream = processHandleCreateStreamRequest(streamBean);
        streamCommonResponse.setStreamId(stream.getStreamId());
        streamCommonResponse.setStatus(StreamConst.STATUS_SUCCESS);
        streamCommonResponse.setNarration(StreamConst.STREAM_CREATE_SUCCESS);
        return streamCommonResponse;
    }

    @Override
    public StreamCommonResponse deleteStream(StreamDeleteRequest streamDeleteRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        streamValidatorI.deleteStreamValidate(streamDeleteRequest);
        handleStreamDeleteRequest(streamDeleteRequest);
        streamCommonResponse.setStreamId(streamDeleteRequest.getStreamId());
        streamCommonResponse.setStatus(StreamConst.STATUS_SUCCESS);
        streamCommonResponse.setNarration(StreamConst.STREAM_DELETE_SUCCESS);
        return streamCommonResponse;
    }

    @Override
    public StreamCommonResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        streamValidatorI.addArticleToStreamValidate(articleToStreamRequest);
        processArticleToStreamAddRequest(articleToStreamRequest);
        streamCommonResponse.setStreamId(articleToStreamRequest.getStreamId());
        streamCommonResponse.setStatus(StreamConst.STATUS_SUCCESS);
        streamCommonResponse.setNarration(StreamConst.ADD_ARTICLES_TO_STREAM_SUCCESS);
        return streamCommonResponse;
    }

    @Override
    public UserMappedStreamResponse findStreamByUser(StreamRequest streamRequest){
        UserMappedStreamResponse userMappedStreamResponse = new UserMappedStreamResponse();
        Optional<UserMappedStream> existingUserMappedStream = streamDAOI.findUserMappedStreamByUserName(streamRequest.getUserName());
        if (existingUserMappedStream.isPresent()) {
            userMappedStreamResponse.setUserMappedStream(existingUserMappedStream.get());
        }
        return userMappedStreamResponse;
    }

    @Override
    public StreamMappedArticleResponse findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest){
        StreamMappedArticleResponse streamMappedArticleResponse = new StreamMappedArticleResponse();
        Optional<StreamMappedArticles> existingStreamMappedArticle = streamDAOI.findByStreamId(streamMappedArticleRequest.getStreamId());
        if (existingStreamMappedArticle.isPresent()){
            streamMappedArticleResponse.setStreamMapArticles(existingStreamMappedArticle.get());
        }
        return streamMappedArticleResponse;
    }

    private void processArticleToStreamAddRequest(ArticleToStreamRequest articleToStreamRequest){
        List<String> articleList = articleToStreamRequest.getArticleIdList();
        Optional<StreamMappedArticles> existingMapArticle =
                streamDAOI.findByStreamId(articleToStreamRequest.getStreamId());

        if (existingMapArticle.isPresent()){
            Map<String, Article> streamMapArticlesMap = existingMapArticle.get().getStreamMapArticlesMap();
            articleList.parallelStream().forEach(
                    articleId -> {
                        Article article = restTemplate.getForObject("http://ARTICLE-SERVICE/article/findArticleDetailsById/"
                                + articleId, Article.class);
                        streamMapArticlesMap.put(articleId, article);
                    }
            );
        }
    }

    private Stream processHandleCreateStreamRequest(StreamBean streamBean){
        Optional<Stream> existingStream = streamDAOI.findStreamById(streamBean.getStreamId());
        Stream newStream = createStream(streamBean);
        if (existingStream.isPresent()){
            //In case of updating stream >> If required log can be added
        } else {
            //Creating new stream flow
            createStreamMapArticle(streamBean);
            createUserMappedStream(streamBean, newStream);
        }
        return newStream;
    }

    private Stream createStream(StreamBean streamBean){
        Stream stream = new Stream();
        stream.setStreamId(streamBean.getStreamId());
        stream.setUserName(streamBean.getUserName());
        streamDAOI.saveStream(stream);
        return stream;
    }

    private void createStreamMapArticle(StreamBean streamBean){
        StreamMappedArticles streamMappedArticles = new StreamMappedArticles();
        streamMappedArticles.setStreamId(streamBean.getStreamId());
        streamMappedArticles.setUserName(streamBean.getUserName());
        streamDAOI.saveStreamMapArticle(streamMappedArticles);
    }

    private void createUserMappedStream(StreamBean streamBean, Stream stream){
        UserMappedStream userMappedStream = new UserMappedStream();
        userMappedStream.setUserName(streamBean.getUserName());
        userMappedStream.getUserStreamMap().put(stream.getStreamId(), stream);
    }

    private void handleStreamDeleteRequest(StreamDeleteRequest streamDeleteRequest){
        streamDAOI.deleteStreamByStreamId(streamDeleteRequest.getStreamId());
        streamDAOI.deleteStreamMappedArticlesByStreamId(streamDeleteRequest.getStreamId());
        streamDAOI.deleteUserMappedStreamByUserId(streamDeleteRequest.getUserName());
    }
}

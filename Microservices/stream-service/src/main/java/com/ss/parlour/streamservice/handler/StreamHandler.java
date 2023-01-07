package com.ss.parlour.streamservice.handler;

import com.ss.parlour.streamservice.dao.cassandra.StreamDAOI;
import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamMapArticles;
import com.ss.parlour.streamservice.domain.cassandra.UserMappedStream;
import com.ss.parlour.streamservice.utils.bean.Article;
import com.ss.parlour.streamservice.utils.bean.StreamBean;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamCommonResponse;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.bean.response.UserMappedStreamResponse;
import com.ss.parlour.streamservice.utils.validator.MainValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StreamHandler implements StreamHandlerI{

    @Autowired
    private MainValidatorI mainValidatorI;

    @Autowired
    private StreamDAOI streamDAOI;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public StreamCommonResponse createStream(StreamCreateRequest streamCreateRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        StreamBean streamBean = mainValidatorI.streamCreateRequestValidate(streamCreateRequest);
        processHandleCreateStreamRequest(streamBean);
        return streamCommonResponse;
    }

    @Override
    public StreamCommonResponse deleteStream(StreamDeleteRequest streamDeleteRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        mainValidatorI.deleteStreamValidate(streamDeleteRequest);
        return streamCommonResponse;
    }

    @Override
    public StreamCommonResponse addArticleToStream(ArticleToStreamRequest articleToStreamRequest){
        StreamCommonResponse streamCommonResponse = new StreamCommonResponse();
        mainValidatorI.addArticleToStreamValidate(articleToStreamRequest);
        processArticleToStreamAddRequest(articleToStreamRequest);
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
        Optional<StreamMapArticles> existingStreamMappedArticle = streamDAOI.findByStreamNameAndAndUserName(streamMappedArticleRequest.getStreamId(), streamMappedArticleRequest.getUserId());
        if (existingStreamMappedArticle.isPresent()){
            streamMappedArticleResponse.setStreamMapArticles(existingStreamMappedArticle.get());
        }
        return streamMappedArticleResponse;
    }

    private void processArticleToStreamAddRequest(ArticleToStreamRequest articleToStreamRequest){
        List<String> articleList = articleToStreamRequest.getArticleIdList();
        Optional<StreamMapArticles> existingMapArticle =
                streamDAOI.findByStreamNameAndAndUserName(articleToStreamRequest.getStreamName(), articleToStreamRequest.getUserName());

        if (existingMapArticle.isPresent()){
            Map<String, Article> streamMapArticlesMap = existingMapArticle.get().getStreamMapArticlesMap();
            articleList.parallelStream().forEach(
                    articleId -> {
                        Article article = restTemplate.getForObject("http://ARTICLE-SERVICE/article/findArticleDetailsById/"
                                + articleId,Article.class);
                        streamMapArticlesMap.put(articleId, article);
                    }

            );
        }
    }

    private void processHandleCreateStreamRequest(StreamBean streamBean){
        Stream stream = createStream(streamBean);
        createStreamMapArticle(streamBean);
        createUserMappedStream(streamBean, stream);
    }

    private Stream createStream(StreamBean streamBean){
        Stream stream = new Stream();
        stream.setStreamName(streamBean.getStreamName());
        stream.setUserName(streamBean.getUserName());
        streamDAOI.saveStream(stream);
        return stream;
    }

    private void createStreamMapArticle(StreamBean streamBean){
        StreamMapArticles streamMapArticles = new StreamMapArticles();
        streamMapArticles.setStreamName(streamBean.getStreamName());
        streamMapArticles.setUserName(streamBean.getUserName());
        streamDAOI.saveStreamMapArticle(streamMapArticles);
    }

    private void createUserMappedStream(StreamBean streamBean, Stream stream){
        UserMappedStream userMappedStream = new UserMappedStream();
        userMappedStream.setUserName(streamBean.getUserName());
        userMappedStream.getUserStreamMap().put(stream.getStreamName(), stream);
    }
}

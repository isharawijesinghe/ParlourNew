package com.ss.parlour.streamservice.handler;

import com.ss.parlour.streamservice.dao.cassandra.StreamDAOI;
import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.utils.bean.*;
import com.ss.parlour.streamservice.utils.bean.requests.*;
import com.ss.parlour.streamservice.utils.bean.response.StreamMappedArticleResponse;
import com.ss.parlour.streamservice.utils.common.DateUtils;
import com.ss.parlour.streamservice.utils.common.StreamKeyGenerator;
import com.ss.parlour.streamservice.utils.exception.StreamServiceRuntimeException;
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
    public String createStream(StreamCreateRequest streamCreateRequest){
        StreamCreateHelperBean streamCreateHelperBean = new StreamCreateHelperBean();
        StreamBean streamBean = streamValidatorI.streamCreateRequestValidate(streamCreateRequest);
        if (streamCreateRequest.getStreamBean().getStreamId() == null || streamCreateRequest.getStreamBean().getStreamId().isEmpty()){
            streamCreateRequest.getStreamBean().setStreamId(streamKeyGenerator.streamKeyGenerator(streamBean.getUserId()));
        }
        populateStreamData(streamCreateHelperBean, streamBean);
        populateStreamByUserData(streamCreateHelperBean, streamBean);
        streamDAOI.saveStream(streamCreateHelperBean);
        return streamCreateRequest.getStreamBean().getStreamId();
    }

    @Override
    public void deleteStream(StreamDeleteRequest streamDeleteRequest){
        StreamDeleteHelperBean streamDeleteHelperBean = new StreamDeleteHelperBean();
        streamValidatorI.deleteStreamValidate(streamDeleteRequest);
        prePopulateStreamDataFromDb(streamDeleteHelperBean, streamDeleteRequest);
        streamDAOI.deleteStreamData(streamDeleteHelperBean);
    }

    @Override
    public void addArticleToStream(ArticleToStreamRequest articleToStreamRequest){
        ArticleStreamAddaHelperBean articleStreamAddaHelperBean = new ArticleStreamAddaHelperBean();
        streamValidatorI.addArticleToStreamValidate(articleToStreamRequest);
        articleToStreamRequest.setCreatedDate(DateUtils.currentSqlTimestamp());
        processArticleToStreamAddRequest(articleStreamAddaHelperBean, articleToStreamRequest);
        streamDAOI.saveStreamArticle(articleStreamAddaHelperBean);
    }

    @Override
    public List<StreamByUser> findStreamByUser(StreamRequest streamRequest){
        Optional<List<StreamByUser>> streamByUserListOptional = streamDAOI.findListOfStreamByUser(streamRequest.getUserName());
        var listOfUsers = streamByUserListOptional
                .orElseThrow(() -> new StreamServiceRuntimeException(""));
        return listOfUsers;
    }

    @Override
    public List<StreamMappedArticles> findArticlesByStream(StreamMappedArticleRequest streamMappedArticleRequest){
        Optional<List<StreamMappedArticles>> streamMappedArticlesListOptional = streamDAOI.findListOfStreamMappedArticles(streamMappedArticleRequest.getStreamId());
        var listOfArticles = streamMappedArticlesListOptional
                .orElseThrow(() -> new StreamServiceRuntimeException(""));
        return listOfArticles;
    }

    //---------------------------********* Stream handler provide methods *********---------------------------//

    protected void populateStreamData(StreamCreateHelperBean streamCreateHelperBean, StreamBean streamBean){
        streamCreateHelperBean.setStream(new Stream(streamBean));
    }

    protected void populateStreamByUserData(StreamCreateHelperBean streamCreateHelperBean, StreamBean streamBean){
        streamCreateHelperBean.setStreamByUser(new StreamByUser(streamBean));
    }

    protected void prePopulateStreamDataFromDb(StreamDeleteHelperBean streamDeleteHelperBean, StreamDeleteRequest streamDeleteRequest){
        Optional<Stream> streamDataOptional = streamDAOI.findStreamData(streamDeleteRequest.getStreamId());
        var stream = streamDataOptional.orElseThrow();
        if (stream != null){
            Optional<StreamByUser> streamByUserOptional = streamDAOI.findStreamByUser(stream.getUserId(), stream.getStreamId(), stream.getCreatedDate());
            streamByUserOptional.ifPresent(streamByUser -> streamDeleteHelperBean.setStreamByUser(streamByUser));
            streamDeleteHelperBean.setStream(stream);
        }
    }

    protected void processArticleToStreamAddRequest(ArticleStreamAddaHelperBean articleStreamAddaHelperBean , ArticleToStreamRequest articleToStreamRequest){
        List<String> articleIdList = articleToStreamRequest.getArticleIdList();
        articleIdList.forEach(
                articleId -> {
                    articleStreamAddaHelperBean.getArticleMappedStreamList().add(new ArticleMappedStream(articleToStreamRequest, articleId));
                    articleStreamAddaHelperBean.getStreamMappedArticlesList().add(new StreamMappedArticles(articleToStreamRequest, articleId));
                }
        );

    }

}

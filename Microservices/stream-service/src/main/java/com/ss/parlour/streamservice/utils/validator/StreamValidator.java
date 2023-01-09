package com.ss.parlour.streamservice.utils.validator;

import com.ss.parlour.streamservice.dao.cassandra.StreamDAOI;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.utils.bean.StreamBean;
import com.ss.parlour.streamservice.utils.bean.StreamErrorCodes;
import com.ss.parlour.streamservice.utils.bean.requests.ArticleToStreamRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamCreateRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamDeleteRequest;
import com.ss.parlour.streamservice.utils.exception.StreamServiceRuntimeException;
import com.thoughtworks.xstream.io.StreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StreamValidator implements StreamValidatorI {

    @Autowired
    private StreamDAOI streamDAOI;

    @Override
    public StreamBean streamCreateRequestValidate(StreamCreateRequest streamCreateRequest){

        return streamCreateRequest.getStreamBean();
    }

    @Override
    public void deleteStreamValidate(StreamDeleteRequest streamDeleteRequest){
        validateExistingArticle(streamDeleteRequest);
    }

    @Override
    public void addArticleToStreamValidate(ArticleToStreamRequest articleToStreamRequest){

    }

    protected void validateExistingArticle(StreamDeleteRequest streamDeleteRequest){
        Optional<StreamMappedArticles> existingStreamMappedArticles = streamDAOI.findByStreamId(streamDeleteRequest.getStreamId());
        if (existingStreamMappedArticles.isPresent()){
            throw new StreamServiceRuntimeException(StreamErrorCodes.STREAM_ASSIGN_ARTICLES_EXISTS);
        }
    }
}

package com.ss.parlour.streamservice.utils.validator;

import com.ss.parlour.streamservice.utils.bean.StreamBean;
import com.ss.parlour.streamservice.utils.bean.requests.ArticleToStreamRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamCreateRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamDeleteRequest;
import org.springframework.stereotype.Component;

@Component
public class MainValidator implements MainValidatorI {

    @Override
    public StreamBean streamCreateRequestValidate(StreamCreateRequest streamCreateRequest){

        return streamCreateRequest.getStreamBean();
    }

    @Override
    public void deleteStreamValidate(StreamDeleteRequest streamDeleteRequest){

    }

    @Override
    public void addArticleToStreamValidate(ArticleToStreamRequest articleToStreamRequest){

    }
}

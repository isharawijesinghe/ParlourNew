package com.ss.parlour.streamservice.utils.validator;

import com.ss.parlour.streamservice.utils.bean.StreamBean;
import com.ss.parlour.streamservice.utils.bean.requests.ArticleToStreamRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamCreateRequest;
import com.ss.parlour.streamservice.utils.bean.requests.StreamDeleteRequest;

public interface MainValidatorI {

    StreamBean streamCreateRequestValidate(StreamCreateRequest streamCreateRequest);
    void deleteStreamValidate(StreamDeleteRequest streamDeleteRequest);
    void addArticleToStreamValidate(ArticleToStreamRequest articleToStreamRequest);
}

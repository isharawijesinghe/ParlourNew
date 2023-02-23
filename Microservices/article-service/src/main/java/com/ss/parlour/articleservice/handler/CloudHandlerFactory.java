package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.handler.cloud.AWSArticleHandler;
import com.ss.parlour.articleservice.handler.cloud.CloudArticleHandler;
import com.ss.parlour.articleservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudHandlerFactory implements CloudHandlerFactoryI{

    @Value("${cloud.provider}")
    private String cloudProvider;

    @Autowired
    private AWSArticleHandler awsArticleHandler;

    @Autowired
    private CloudArticleHandler cloudArticleHandler;


    @Override
    public CommonCloudHandlerI getCloudHandler() {
        if (cloudProvider.equals(ArticleConst.AWS_CLOUD_PROVIDER)){
            return awsArticleHandler;
        }
        return cloudArticleHandler;
    }
}

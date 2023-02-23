package com.ss.parlour.articleservice.handler.cloud;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponseBean;
import org.springframework.stereotype.Component;

@Component
public class CloudArticleHandler implements CommonCloudHandlerI{

    @Override
    public PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        return null;
    }
}

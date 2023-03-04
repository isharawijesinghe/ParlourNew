package com.ss.parlour.articleservice.handler.cloud;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;
import org.springframework.stereotype.Component;

@Component
public class CloudArticleHandler implements CommonCloudHandlerI{

    @Override
    public PreSignUrlResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest){
        return null;
    }
}

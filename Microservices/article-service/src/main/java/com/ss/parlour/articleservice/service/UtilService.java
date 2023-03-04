package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.articleservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService implements UtilServiceI {

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Override
    public PreSignUrlResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest){
        CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
        return commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequest);
    }

}

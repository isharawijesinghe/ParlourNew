package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.articleservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService implements UtilServiceI {

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Override
    public PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
        return commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequestBean);
    }

}

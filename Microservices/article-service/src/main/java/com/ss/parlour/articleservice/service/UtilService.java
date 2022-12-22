package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.image.aws.AwsImageHandlerI;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService implements UtilServiceI {

    @Autowired
    private AwsImageHandlerI imageHandlerI;

    @Override
    public String generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        return imageHandlerI.generatePreSignUrl(preSignUrlGenerateRequestBean);
    }

    @Override
    public String findFileByName(PreSignUrlRequestBean preSignUrlRequestBean){
        return imageHandlerI.findFileByName(preSignUrlRequestBean);
    }

}

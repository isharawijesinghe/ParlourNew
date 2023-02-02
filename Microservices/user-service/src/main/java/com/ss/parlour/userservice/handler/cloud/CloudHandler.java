package com.ss.parlour.userservice.handler.cloud;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import org.springframework.stereotype.Component;

@Component
public class CloudHandler implements CommonCloudHandlerI{

    @Override
    public PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        return null;
    }
}

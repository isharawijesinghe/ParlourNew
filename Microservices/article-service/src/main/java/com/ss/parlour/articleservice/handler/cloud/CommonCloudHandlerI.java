package com.ss.parlour.articleservice.handler.cloud;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;

public interface CommonCloudHandlerI {

    PreSignUrlResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest);

}

package com.ss.parlour.articleservice.handler.cloud;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponseBean;

public interface CommonCloudHandlerI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);

}

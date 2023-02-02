package com.ss.parlour.userservice.handler.cloud;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;

public interface CommonCloudHandlerI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);

}

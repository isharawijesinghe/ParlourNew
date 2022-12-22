package com.ss.parlour.articleservice.handler.image.aws;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;

public interface AwsImageHandlerI {

    String generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
    String findFileByName(PreSignUrlRequestBean preSignUrlRequestBean);
}

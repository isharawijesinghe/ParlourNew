package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;

public interface UtilServiceI {

    PreSignUrlResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest);
}

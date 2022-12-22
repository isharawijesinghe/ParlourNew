package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;

public interface UtilServiceI {

    String generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
    String findFileByName(PreSignUrlRequestBean preSignUrlRequestBean);
}

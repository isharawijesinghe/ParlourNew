package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponseBean;

public interface UtilServiceI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
}

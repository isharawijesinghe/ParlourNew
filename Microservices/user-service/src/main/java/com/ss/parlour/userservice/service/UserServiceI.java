package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;

public interface UserServiceI {

    PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean);
}

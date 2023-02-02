package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;

public interface UserValidatorI {

    void validatePreSignUrlRequest(PreSignUrlGenerateRequestBean generatePreSignUrl);
}

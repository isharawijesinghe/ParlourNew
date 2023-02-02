package com.ss.parlour.userservice.util.validators;

import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements UserValidatorI{

    @Override
    public void validatePreSignUrlRequest(PreSignUrlGenerateRequestBean generatePreSignUrl){}
}

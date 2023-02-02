package com.ss.parlour.userservice.service;

import com.ss.parlour.userservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.userservice.util.bean.UserErrorCodes;
import com.ss.parlour.userservice.util.bean.requests.PreSignUrlGenerateRequestBean;
import com.ss.parlour.userservice.util.bean.response.PreSignUrlResponseBean;
import com.ss.parlour.userservice.util.exception.UserRuntimeException;
import com.ss.parlour.userservice.util.validators.UserValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService implements UserServiceI{

    @Autowired
    private UserValidatorI userValidatorI;

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Override
    public PreSignUrlResponseBean generatePreSignUrl(PreSignUrlGenerateRequestBean preSignUrlGenerateRequestBean){
        try{
            userValidatorI.validatePreSignUrlRequest(preSignUrlGenerateRequestBean);
            CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
            return commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequestBean);
        }catch (UserRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new UserRuntimeException(UserErrorCodes.UNKNOWN_ERROR, ex);
        }
    }
}

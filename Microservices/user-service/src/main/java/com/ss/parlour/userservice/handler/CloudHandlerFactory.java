package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.handler.cloud.AWSUserHandler;
import com.ss.parlour.userservice.handler.cloud.CloudHandler;
import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.userservice.util.bean.UserConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudHandlerFactory implements CloudHandlerFactoryI{

    @Value("${cloud.provider}")
    private String cloudProvider;

    @Autowired
    private AWSUserHandler awsUserHandler;

    @Autowired
    private CloudHandler cloudHandler;


    @Override
    public CommonCloudHandlerI getCloudHandler() {
        if (cloudProvider.equals(UserConst.AWS_CLOUD_PROVIDER)){
            return awsUserHandler;
        }
        return cloudHandler;
    }
}

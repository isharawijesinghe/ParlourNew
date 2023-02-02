package com.ss.parlour.userservice.handler;

import com.ss.parlour.userservice.handler.cloud.CommonCloudHandlerI;

public interface CloudHandlerFactoryI {

    CommonCloudHandlerI getCloudHandler();
}

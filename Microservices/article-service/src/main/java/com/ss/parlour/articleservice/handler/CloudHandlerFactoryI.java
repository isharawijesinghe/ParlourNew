package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.handler.cloud.CommonCloudHandlerI;

public interface CloudHandlerFactoryI {

    CommonCloudHandlerI getCloudHandler();
}

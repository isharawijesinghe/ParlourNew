package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.CloudHandlerFactoryI;
import com.ss.parlour.articleservice.handler.cloud.CommonCloudHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.PreSignUrlGenerateRequest;
import com.ss.parlour.articleservice.utils.bean.response.PreSignUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class UtilService implements UtilServiceI {

    @Autowired
    private CloudHandlerFactoryI cloudHandlerFactoryI;

    @Override
    public ArticleResponse generatePreSignUrl(PreSignUrlGenerateRequest preSignUrlGenerateRequest){
        CommonCloudHandlerI commonCloudHandlerI = cloudHandlerFactoryI.getCloudHandler();
        PreSignUrlResponse preSignUrlResponse = commonCloudHandlerI.generatePreSignUrl(preSignUrlGenerateRequest);
        return  ArticleResponse.builder().body(preSignUrlResponse)
                .articleMsgHeader(preSignUrlGenerateRequest.getArticleMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(ArticleConst.ARTICLE_IMAGE_UPLOAD_PRE_SIGNED_GENERATE_SUCCESSFUL_NARRATION)
                .build();
    }

}

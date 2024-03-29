package com.ss.parlour.articleservice.handler.like;

import com.ss.parlour.articleservice.handler.LikeTypeHandlerFactoryI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.LikeCommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeHandler implements LikeHandlerI{

    @Autowired
    private LikeTypeHandlerFactoryI likeHandlerFactoryI;

    //When user click on like / unlike button from article or comment
    @Override
    public LikeCommonResponse processAddLikeRequest(LikeRequestBean likeRequestBean) {
        LikeCommonResponse likeCommonResponse = new LikeCommonResponse();
        likeHandlerFactoryI.getLikeTypeHandlerI(likeRequestBean.getLikeType()).addLikeRequest(likeRequestBean);
        likeCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        likeCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_LIKE_ADDED);
        return likeCommonResponse;
    }

}

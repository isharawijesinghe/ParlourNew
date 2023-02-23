package com.ss.parlour.articleservice.handler.like;

import com.ss.parlour.articleservice.handler.LikeTypeHandlerFactoryI;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeHandler implements LikeHandlerI{

    @Autowired
    private LikeTypeHandlerFactoryI likeHandlerFactoryI;

    //When user click on like / unlike button from article or comment
    @Override
    public void processAddLikeRequest(LikeBean likeBean) {
        likeHandlerFactoryI.getLikeTypeHandlerI(likeBean.getLikeType()).handleLikeRequest(likeBean);
    }

}

package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.utils.bean.LikeBean;

public interface LikeTypeHandlerI {

    void handleLikeRequest(LikeBean likeBean);
}

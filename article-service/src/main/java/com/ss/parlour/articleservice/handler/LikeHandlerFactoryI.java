package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.utils.bean.LikeType;

public interface LikeHandlerFactoryI {

    LikeHandlerI getLikeHandlerI(LikeType likeType);
}

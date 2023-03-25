package com.ss.parlour.articleservice.handler.like;

import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.LikeCommonResponse;

public interface LikeHandlerI {

    LikeCommonResponse processAddLikeRequest(LikeRequestBean likeRequestBean);
}

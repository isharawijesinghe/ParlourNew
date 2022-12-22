package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.domain.cassandra.Like;

public interface LikeTypeHandlerI {

    void handleLikeType(Like like);
}

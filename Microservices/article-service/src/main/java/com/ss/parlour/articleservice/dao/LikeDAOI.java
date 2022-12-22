package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.domain.cassandra.Like;

import java.util.Optional;

public interface LikeDAOI {

    Optional<Like> getLikeByKey(String key);
    void saveLike(Like like);
}

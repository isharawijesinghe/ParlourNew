package com.ss.parlour.articleservice.dao;

import com.ss.parlour.articleservice.repository.cassandra.LikeRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeDAO implements LikeDAOI{

    @Autowired
    private LikeRepositoryI likeRepositoryI;

}

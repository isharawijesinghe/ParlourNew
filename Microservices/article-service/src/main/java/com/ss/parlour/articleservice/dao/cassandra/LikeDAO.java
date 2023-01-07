package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.repository.cassandra.LikeRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LikeDAO implements LikeDAOI{

    @Autowired
    private LikeRepositoryI likeRepositoryI;

    @Override
    public Optional<Like> getLikeByKey(String key){
        return likeRepositoryI.findByKey(key);
    }

    @Override
    public void saveLike(Like like){
        likeRepositoryI.save(like);
    }
}

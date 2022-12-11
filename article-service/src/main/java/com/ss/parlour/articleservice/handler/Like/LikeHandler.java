package com.ss.parlour.articleservice.handler.Like;

import com.ss.parlour.articleservice.dao.LikeDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerFactoryI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LikeHandler implements LikeHandlerI{

    @Autowired
    private LikeDAOI likeDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private LikeTypeHandlerFactoryI likeHandlerFactoryI;

    @Override
    public void handleLikeRequest(LikeBean likeBean) {
        Like like = populateLike(likeBean);
        handleLike(like);
        handleLikeType(like);
    }

    protected void handleLike(Like like){
        String likeKey = keyGenerator.getLikeKey(like.getArticleId(), like.getCommentId(), like.getUserName());
        Optional<Like> existingLike = likeDAOI.getLikeByKey(likeKey);
        if (existingLike.isPresent()){
            if (existingLike.get().getStatus() != like.getStatus()){
                like.setStatus(ArticleConst.USER_NEUTRAL);
            }
        }
        likeDAOI.saveLike(like);
    }

    public void handleLikeType(Like like){
        likeHandlerFactoryI.getLikeTypeHandlerI(like.getLikeType()).handleLikeType(like);
    }

    protected Like populateLike(LikeBean likeBean){
        String likeKey = keyGenerator.getLikeKey(likeBean.getArticleId(), likeBean.getCommentId(),
                likeBean.getUserName());
        Like like = new Like();
        like.setKey(likeKey);
        like.setArticleId(likeBean.getArticleId());
        like.setCommentId(likeBean.getCommentId());
        like.setLikeType(likeBean.getLikeType());
        like.setUserName(likeBean.getUserName());
        like.setStatus(likeBean.getStatus());
        like.setCreatedDate(likeBean.getCreatedDate());
        like.setModifiedDate(likeBean.getModifiedDate());
        return like;
    }
}

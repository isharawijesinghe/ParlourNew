package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.handler.article.ArticleHandler;
import com.ss.parlour.articleservice.handler.comment.CommentHandler;
import com.ss.parlour.articleservice.utils.bean.LikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeHandlerFactory implements LikeHandlerFactoryI{

    @Autowired
    private ArticleHandler articleHandler;

    @Autowired
    private CommentHandler commentHandler;

    public LikeHandlerI getLikeHandlerI(LikeType likeType){
        if (likeType == LikeType.ARTICLE){
            return articleHandler;
        }
        return commentHandler;
    }

}

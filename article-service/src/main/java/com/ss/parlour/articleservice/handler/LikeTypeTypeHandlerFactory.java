package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.handler.article.ArticleHandler;
import com.ss.parlour.articleservice.handler.comment.CommentTypeHandler;
import com.ss.parlour.articleservice.utils.bean.LikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeTypeTypeHandlerFactory implements LikeTypeHandlerFactoryI {

    @Autowired
    private ArticleHandler articleHandler;

    @Autowired
    private CommentTypeHandler commentHandler;

    public LikeTypeHandlerI getLikeTypeHandlerI(LikeType likeType){
        if (likeType == LikeType.ARTICLE){
            return articleHandler;
        }
        return commentHandler;
    }

}

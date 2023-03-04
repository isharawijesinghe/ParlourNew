package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import org.springframework.stereotype.Component;

@Component
public class ArticleValidator implements ArticleValidatorI{

    @Override
    public ArticleBean validateArticleRequest(ArticleCreateRequest articleCreateRequest){
        //Validate for article existence
        //Validate for user exist
        //Validate for fields exist
        return articleCreateRequest.getArticleBean();
    }

    @Override
    public CommentBean validateCommentRequest(CommentCreateRequest commentCreateRequest){
        return commentCreateRequest.getCommentBean();
    }

    @Override
    public LikeBean validateArticleLikeRequest(LikeRequest likeRequest){
        return likeRequest.getLikeBean();
    }

    @Override
    public void validateArticleDeleteRequest(ArticleDeleteRequest articleDeleteRequest){

    }

    @Override
    public void validateCommentDeleteRequest(CommentDeleteRequest commentDeleteRequest){

    }
}

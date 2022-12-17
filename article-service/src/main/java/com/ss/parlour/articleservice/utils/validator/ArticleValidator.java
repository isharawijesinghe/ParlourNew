package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import org.springframework.stereotype.Component;

@Component
public class ArticleValidator implements ArticleValidatorI{

    @Override
    public ArticleBean validateArticleRequest(ArticleCreateRequestBean articleCreateRequestBean){
        return articleCreateRequestBean.getArticleBean();
    }

    @Override
    public CommentBean validateCommentRequest(CommentCreateRequestBean commentCreateRequestBean){
        return commentCreateRequestBean.getCommentBean();
    }

    @Override
    public LikeBean validateArticleLikeRequest(LikeRequestBean likeRequestBean){
        return likeRequestBean.getArticleLikeBean();
    }

    @Override
    public void validateArticleDeleteRequest(ArticleDeleteRequestBean articleDeleteRequestBean){

    }

    @Override
    public void validateCommentDeleteRequest(CommentDeleteRequestBean commentDeleteRequestBean){

    }
}

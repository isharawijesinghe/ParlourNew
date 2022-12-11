package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.handler.Like.LikeHandlerI;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.validator.ArticleValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonArticleHandler implements CommonArticleHandlerI {

    @Autowired
    private ArticleHandlerI articleHandlerI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Autowired
    private ArticleValidatorI articleValidatorI;

    @Autowired
    private LikeTypeHandlerFactoryI likeHandlerFactoryI;

    @Autowired
    private LikeHandlerI likeHandlerI;

    @Override
    public ArticleCommonResponseBean handleArticleRequest(ArticleRequestBean articleRequestBean){
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleRequestBean);
        articleHandlerI.handleArticleRequest(articleBean);
        return new ArticleCommonResponseBean(ArticleConst.STATUS_SUCCESS, ArticleConst.SUCCESSFULLY_CREATED_ARTICLE);//Return response
    }

    @Override
    public ArticleCommonResponseBean handleCommentRequest(CommentRequestBean commentRequestBean){
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentRequestBean); //Doing basic initial validations
        commentHandlerI.handleCommentRequest(commentBean); //Handle comments related logics
        return new ArticleCommonResponseBean(ArticleConst.STATUS_SUCCESS, ArticleConst.SUCCESSFULLY_COMMENT_ADDED);//Return response
    }

    @Override
    public ArticleCommonResponseBean handleLikeRequest(LikeRequestBean likeRequestBean){
        LikeBean likeBean = articleValidatorI.validateArticleLikeRequest(likeRequestBean); //Doing basic initial validations
        likeHandlerI.handleLikeRequest(likeBean);
        return new ArticleCommonResponseBean(ArticleConst.STATUS_SUCCESS, ArticleConst.SUCCESSFULLY_COMMENT_ADDED);//Return response
    }


}

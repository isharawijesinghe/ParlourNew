package com.ss.parlour.articleservice.utils.validator;

import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;
import org.springframework.stereotype.Component;

@Component
public class ArticleValidator implements ArticleValidatorI{

    public ArticleBean validateArticleRequest(ArticleRequestBean articleRequestBean){
        return articleRequestBean.getArticleBean();
    }

    public CommentBean validateCommentRequest(CommentRequestBean commentRequestBean){
        return commentRequestBean.getCommentBean();
    }

    public LikeBean validateArticleLikeRequest(LikeRequestBean likeRequestBean){
        return likeRequestBean.getArticleLikeBean();
    }
}

package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.CommonArticleHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleErrorCodes;
import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private CommonArticleHandlerI commonArticleHandlerI;

    //Service call for create article
    @Override
    public ArticleCommonResponseBean createArticle(ArticleRequestBean articleRequestBean){
        try {
            return commonArticleHandlerI.handleArticleRequest(articleRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    //Service call for creating user comment for article
    @Override
    public ArticleCommonResponseBean createComment(CommentRequestBean commentRequestBean){
        try {
            return commonArticleHandlerI.handleCommentRequest(commentRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    //Service call for add like for user article or comment
    @Override
    public ArticleCommonResponseBean addLike(LikeRequestBean likeRequestBean){
        try {
            return commonArticleHandlerI.handleLikeRequest(likeRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }
}

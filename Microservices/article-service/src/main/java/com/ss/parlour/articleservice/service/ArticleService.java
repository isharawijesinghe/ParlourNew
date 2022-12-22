package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.CommonArticleHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleErrorCodes;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private CommonArticleHandlerI commonArticleHandlerI;

    //Service call for create article
    @Override
    public ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean){
        try {
            return commonArticleHandlerI.handleArticleRequest(articleCreateRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    //Service call for creating user comment for article
    @Override
    public ArticleCommonResponseBean createComment(CommentCreateRequestBean commentCreateRequestBean){
        try {
            return commonArticleHandlerI.handleCommentRequest(commentCreateRequestBean);
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

    //Load article details by article id
    @Override
    public ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean){
        try {
            return commonArticleHandlerI.findArticleById(articleRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean){
        try {
            return commonArticleHandlerI.findArticleHistoryById(articleHistoryRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    //Service call for delete article
    @Override
    public ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean){
        try {
            return commonArticleHandlerI.handleArticleDelete(articleDeleteRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    //Service call for delete comment
    @Override
    public ArticleCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        try {
            return commonArticleHandlerI.deleteComment(commentDeleteRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

}

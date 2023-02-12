package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.handler.CommonArticleHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleErrorCodes;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public CommentCommonResponseBean createComment(CommentCreateRequestBean commentCreateRequestBean){
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
    public LikeCommonResponseBean addLike(LikeRequestBean likeRequestBean){
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
    public CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        try {
            return commonArticleHandlerI.deleteComment(commentDeleteRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        try {
            return commonArticleHandlerI.findArticleDetailsById(articleId);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ArticleEditRequestResponse createArticleEditRequest(ArticleEditRequest articleEditRequest){
        try {
            return commonArticleHandlerI.createArticleEditRequest(articleEditRequest);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        try {
            return commonArticleHandlerI.approveArticleEditRequest(articleEditApproveRequest);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }

}

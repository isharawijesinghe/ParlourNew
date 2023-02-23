package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.handler.CommonArticleHandlerI;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private CommonArticleHandlerI commonArticleHandlerI;

    //Service call for create article
    @Override
    public ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean){
        return commonArticleHandlerI.createArticle(articleCreateRequestBean);
    }

    //Service call for creating user comment for article
    @Override
    public CommentCommonResponseBean addComment(CommentCreateRequestBean commentCreateRequestBean){
        return commonArticleHandlerI.addComment(commentCreateRequestBean);
    }

    //Service call for add like for user article or comment
    @Override
    public LikeCommonResponseBean addLike(LikeRequestBean likeRequestBean){
        return commonArticleHandlerI.addLike(likeRequestBean);
    }

    //Load article details by article id
    @Override
    public ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean){
        return commonArticleHandlerI.findArticleById(articleRequestBean);
    }

    //Find article history by article id
    @Override
    public ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean){
        return commonArticleHandlerI.findArticleHistoryById(articleHistoryRequestBean);
    }

    //Service call for delete article
    @Override
    public ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean){
        return commonArticleHandlerI.deleteArticle(articleDeleteRequestBean);
    }

    //Service call for delete comment
    @Override
    public CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        return commonArticleHandlerI.deleteComment(commentDeleteRequestBean);
    }

    //Find article detail by id
    @Override
    public Article findArticleDetailsById(String articleId){
        return commonArticleHandlerI.findArticleDetailsById(articleId);
    }

    //Process article edit request
    @Override
    public ArticleEditRequestResponse articleEditRequest(ArticleEditRequestBean articleEditRequestBean){
        return commonArticleHandlerI.articleEditRequest(articleEditRequestBean);
    }

    //Approve article edit request
    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        return commonArticleHandlerI.approveArticleEditRequest(articleEditApproveRequest);
    }

}

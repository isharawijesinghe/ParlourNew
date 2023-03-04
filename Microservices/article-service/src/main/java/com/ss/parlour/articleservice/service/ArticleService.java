package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.handler.like.LikeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.validator.ArticleValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private ArticleHandlerI articleHandlerI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Autowired
    private ArticleValidatorI articleValidatorI;

    @Autowired
    private LikeHandlerI likeHandlerI;

    @Override
    public ArticleCommonResponse createArticle(ArticleCreateRequest articleCreateRequest){
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequest);
        return articleHandlerI.processCreateArticleRequest(articleBean);
    }

    @Override
    public CommentCommonResponse addComment(CommentCreateRequest commentCreateRequest){
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        return commentHandlerI.processAddCommentRequest(commentBean);
    }

    @Override
    public LikeCommonResponse addLike(LikeRequest likeRequest){
        LikeBean likeBean = articleValidatorI.validateArticleLikeRequest(likeRequest); //Doing basic initial validations
        return likeHandlerI.processAddLikeRequest(likeBean);
    }

    @Override
    public ArticleResponse findArticleById(ArticleRequest articleRequest){
        return articleHandlerI.findArticleById(articleRequest);
    }

    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        return articleHandlerI.findArticleHistoryById(articleHistoryRequest);
    }

    @Override
    public ArticleCommonResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        return articleHandlerI.processDeleteArticleRequest(articleDeleteRequest);
    }

    @Override
    public CommentCommonResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequest); //Doing basic initial validations
        return commentHandlerI.processDeleteCommentRequest(commentDeleteRequest);
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        return articleHandlerI.findArticleDetailsById(articleId);
    }

    @Override
    public ArticleEditRequestResponse articleEditRequest(ArticleEditRequest articleEditRequest){
        return articleHandlerI.processArticleEditRequest(articleEditRequest);
    }

    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        return articleHandlerI.processArticleEditRequestApproval(articleEditApproveRequest);
    }

    @Override
    public ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        return articleHandlerI.postArticleEditDraft(articleEditDraftRequest);
    }

}

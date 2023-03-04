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
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequest);
        Article article = articleHandlerI.processCreateArticleRequest(articleBean);
        articleCommonResponse.setArticleId(article.getId());
        articleCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_CREATED_ARTICLE);
        return articleCommonResponse;
    }

    @Override
    public CommentCommonResponse addComment(CommentCreateRequest commentCreateRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        Comment comment = commentHandlerI.processAddCommentRequest(commentBean); //Handle comments related logics
        commentCommonResponse.setCommentId(comment.getId());
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_ADDED);
        return commentCommonResponse;
    }

    @Override
    public LikeCommonResponse addLike(LikeRequest likeRequest){
        LikeCommonResponse likeCommonResponse = new LikeCommonResponse();
        LikeBean likeBean = articleValidatorI.validateArticleLikeRequest(likeRequest); //Doing basic initial validations
        likeHandlerI.processAddLikeRequest(likeBean);
        likeCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        likeCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_LIKE_ADDED);
        return likeCommonResponse;
    }

    @Override
    public ArticleResponse findArticleById(ArticleRequest articleRequest){
        ArticleResponse articleResponse = articleHandlerI.findArticleById(articleRequest);
        return articleResponse;
    }

    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = articleHandlerI.findArticleHistoryById(articleHistoryRequest);
        return articleHistoryResponse;
    }

    @Override
    public ArticleCommonResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        articleHandlerI.processDeleteArticleRequest(articleDeleteRequest);
        articleCommonResponse.setArticleId(articleDeleteRequest.getArticleId());
        articleCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED);
        return articleCommonResponse;//Return response
    }

    @Override
    public CommentCommonResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        CommentCommonResponse commentCommonResponse = new CommentCommonResponse();
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequest); //Doing basic initial validations
        commentHandlerI.processDeleteCommentRequest(commentDeleteRequest);
        commentCommonResponse.setCommentId(commentDeleteRequest.getCommentId());
        commentCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_DELETED);
        return commentCommonResponse;//Return response
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        return articleHandlerI.findArticleDetailsById(articleId);
    }

    @Override
    public ArticleEditRequestResponse articleEditRequest(ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = articleHandlerI.processArticleEditRequest(articleEditRequest);
        return articleEditRequestResponse;
    }

    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = articleHandlerI.processArticleEditRequestApproval(articleEditApproveRequest);
        return articleEditApproveResponse;
    }

    @Override
    public ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditDraftResponse = articleHandlerI.postArticleEditDraft(articleEditDraftRequest);
        return articleEditDraftResponse;
    }

}

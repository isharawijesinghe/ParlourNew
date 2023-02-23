package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Comment;
import com.ss.parlour.articleservice.handler.like.LikeHandlerI;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
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
    private LikeHandlerI likeHandlerI;

    @Override
    public ArticleCommonResponseBean createArticle(ArticleCreateRequestBean articleCreateRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = new ArticleCommonResponseBean();
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequestBean);
        Article article = articleHandlerI.processCreateArticleRequest(articleBean);
        articleCommonResponseBean.setArticleId(article.getId());
        articleCommonResponseBean.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponseBean.setNarration(ArticleConst.SUCCESSFULLY_CREATED_ARTICLE);
        return articleCommonResponseBean;
    }

    @Override
    public CommentCommonResponseBean addComment(CommentCreateRequestBean commentCreateRequestBean){
        CommentCommonResponseBean commentCommonResponseBean = new CommentCommonResponseBean();
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequestBean); //Doing basic initial validations
        Comment comment = commentHandlerI.processAddCommentRequest(commentBean); //Handle comments related logics
        commentCommonResponseBean.setCommentId(comment.getId());
        commentCommonResponseBean.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponseBean.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_ADDED);
        return commentCommonResponseBean;
    }

    @Override
    public LikeCommonResponseBean addLike(LikeRequestBean likeRequestBean){
        LikeCommonResponseBean likeCommonResponseBean  = new LikeCommonResponseBean();
        LikeBean likeBean = articleValidatorI.validateArticleLikeRequest(likeRequestBean); //Doing basic initial validations
        likeHandlerI.processAddLikeRequest(likeBean);
        likeCommonResponseBean.setStatus(ArticleConst.STATUS_SUCCESS);
        likeCommonResponseBean.setNarration(ArticleConst.SUCCESSFULLY_LIKE_ADDED);
        return likeCommonResponseBean;
    }

    @Override
    public ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean){
        ArticleResponseBean articleResponseBean = articleHandlerI.findArticleById(articleRequestBean);
        return articleResponseBean;
    }

    @Override
    public ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean){
        ArticleHistoryResponseBean articleHistoryResponseBean = articleHandlerI.findArticleHistoryById(articleHistoryRequestBean);
        return articleHistoryResponseBean;
    }

    @Override
    public ArticleCommonResponseBean deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = new ArticleCommonResponseBean();
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequestBean); //Doing basic initial validations
        articleHandlerI.processDeleteArticleRequest(articleDeleteRequestBean);
        articleCommonResponseBean.setArticleId(articleDeleteRequestBean.getArticleId());
        articleCommonResponseBean.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponseBean.setNarration(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED);
        return articleCommonResponseBean;//Return response
    }

    @Override
    public CommentCommonResponseBean deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        CommentCommonResponseBean commentCommonResponseBean = new CommentCommonResponseBean();
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequestBean); //Doing basic initial validations
        commentHandlerI.processDeleteCommentRequest(commentDeleteRequestBean);
        commentCommonResponseBean.setCommentId(commentDeleteRequestBean.getCommentId());
        commentCommonResponseBean.setStatus(ArticleConst.STATUS_SUCCESS);
        commentCommonResponseBean.setNarration(ArticleConst.SUCCESSFULLY_COMMENT_DELETED);
        return commentCommonResponseBean;//Return response
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        return articleHandlerI.findArticleDetailsById(articleId);
    }

    @Override
    public ArticleEditRequestResponse articleEditRequest(ArticleEditRequestBean articleEditRequestBean){
        ArticleEditRequestResponse articleEditRequestResponse = new ArticleEditRequestResponse();
        articleHandlerI.processArticleEditRequest(articleEditRequestBean);
        return articleEditRequestResponse;
    }

    @Override
    public ArticleEditApproveResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = new ArticleEditApproveResponse();
        articleHandlerI.processArticleEditRequestApproval(articleEditApproveRequest);
        return articleEditApproveResponse;
    }

}

package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
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

import java.util.List;

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
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        return commentHandlerI.processAddCommentRequest(commentBean);
    }

    @Override
    public LikeCommonResponse addLike(LikeRequest likeRequest){
        LikeBean likeBean = articleValidatorI.validateAddLikeRequest(likeRequest); //Doing basic initial validations
        return likeHandlerI.processAddLikeRequest(likeBean);
    }

    @Override
    public ArticleResponse findArticleById(String articleId){
        return articleHandlerI.findArticleById(articleId);
    }

    @Override
    public ArticleListResponse findArticleByUser(ArticleListRequest articleListRequest){
        ArticleListResponse  articleListResponse = new ArticleListResponse();
        List<Article> currentUserArticleList = articleHandlerI.findArticleByUser(articleListRequest);
        articleListResponse.setArticleResponseList(currentUserArticleList);
        return articleListResponse;
    }

    @Override
    public ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        return articleHandlerI.findArticleHistoryById(articleHistoryRequest);
    }

    @Override
    public ArticleCommonResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        ArticleCommonResponse articleCommonResponse = new ArticleCommonResponse();
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        articleHandlerI.processDeleteArticleRequest(articleDeleteRequest);
        articleCommonResponse.setArticleId(articleDeleteRequest.getArticleId());
        articleCommonResponse.setStatus(ArticleConst.STATUS_SUCCESS);
        articleCommonResponse.setNarration(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED);
        return articleCommonResponse;
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

    @Override
    public TopicAddResponse addTopic(TopicAddRequest topicAddRequest){
        return articleHandlerI.addTopic(topicAddRequest);
    }

    @Override
    public TopicResponse findAllTopic(){
        return articleHandlerI.findAllTopic();
    }

}

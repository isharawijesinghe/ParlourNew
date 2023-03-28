package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.handler.article.ArticleHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.handler.like.LikeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.common.ArticleMsgHeader;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import com.ss.parlour.articleservice.utils.validator.ArticleValidatorI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest){
        ArticleBean articleBean = articleValidatorI.validateArticleRequest(articleCreateRequest);
        ArticleCommonResponse articleCommonResponse = articleHandlerI.processCreateArticleRequest(articleBean);
        return  ArticleResponse.builder().body(articleCommonResponse)
                .articleMsgHeader(articleCreateRequest.getArticleMsgHeader())
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .message(ArticleConst.SUCCESSFULLY_CREATED_ARTICLE)
                .build();
    }

    @Override
    public ArticleResponse addComment(CommentCreateRequest commentCreateRequest){
        CommentBean commentBean = articleValidatorI.validateCommentRequest(commentCreateRequest); //Doing basic initial validations
        CommentCommonResponse commentCommonResponse = commentHandlerI.processAddCommentRequest(commentBean);
        return  ArticleResponse.builder().body(commentCommonResponse)
                .articleMsgHeader(commentCreateRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_COMMENT_ADDED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse addLike(LikeRequest likeRequest){
        LikeRequestBean likeRequestBean = articleValidatorI.validateAddLikeRequest(likeRequest); //Doing basic initial validations
        LikeCommonResponse likeCommonResponse = likeHandlerI.processAddLikeRequest(likeRequestBean);
        return  ArticleResponse.builder().body(likeCommonResponse)
                .articleMsgHeader(likeRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LIKE_ADDED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findArticleById(String articleId){
        ArticleDetailsResponse articleDetailsResponse =  articleHandlerI.findArticleById(articleId);
        return  ArticleResponse.builder().body(articleDetailsResponse)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LIKE_ADDED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findArticleByUser(ArticleListRequest articleListRequest){
        ArticleListResponse articleListResponse = articleHandlerI.findArticleByUser(articleListRequest);
        return  ArticleResponse.builder().body(articleListResponse)
                .articleMsgHeader(articleListRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_LIKE_ADDED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = articleHandlerI.findArticleHistoryById(articleHistoryRequest);
        return  ArticleResponse.builder().body(articleHistoryResponse)
                .articleMsgHeader(articleHistoryRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_HISTORY_SEARCH_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        articleValidatorI.validateArticleDeleteRequest(articleDeleteRequest); //Doing basic initial validations
        ArticleCommonResponse articleCommonResponse =  articleHandlerI.processDeleteArticleRequest(articleDeleteRequest);
        return  ArticleResponse.builder().body(articleCommonResponse)
                .articleMsgHeader(articleDeleteRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_ARTICLE_DELETED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        articleValidatorI.validateCommentDeleteRequest(commentDeleteRequest); //Doing basic initial validations
        CommentCommonResponse commentCommonResponse = commentHandlerI.processDeleteCommentRequest(commentDeleteRequest);
        return  ArticleResponse.builder().body(commentCommonResponse)
                .articleMsgHeader(commentDeleteRequest.getArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_COMMENT_DELETED)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findArticleComments(CommentRequest commentRequest){
        CommentResponse commentResponse = commentHandlerI.findArticleComments(commentRequest);
        return  ArticleResponse.builder().body(commentResponse)
                .articleMsgHeader(commentRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findArticleDetailsById(String articleId){
        Article article = articleHandlerI.findArticleDetailsById(articleId);
        return  ArticleResponse.builder().body(article)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.ARTICLE_COMMENT_LOAD_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse articleEditRequest(ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = articleHandlerI.processArticleEditRequest(articleEditRequest);
        return  ArticleResponse.builder().body(articleEditRequestResponse)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_PLACE_EDIT_REQUEST)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse approveArticleEditRequest(ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = articleHandlerI.processArticleEditRequestApproval(articleEditApproveRequest);
        return  ArticleResponse.builder().body(articleEditApproveResponse)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.SUCCESSFULLY_APPROVED_EDIT_REQUEST)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditDraftResponse = articleHandlerI.postArticleEditDraft(articleEditDraftRequest);
        return  ArticleResponse.builder().body(articleEditDraftResponse)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.ARTICLE_EDIT_DRAFT_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse addTopic(TopicAddRequest topicAddRequest){
        TopicAddResponse topicAddResponse = articleHandlerI.addTopic(topicAddRequest);
        return  ArticleResponse.builder().body(topicAddResponse)
                .articleMsgHeader(topicAddRequest.getArticleMsgHeader())
                .message(ArticleConst.ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

    @Override
    public ArticleResponse findAllTopic(){
        TopicResponse topicResponse = articleHandlerI.findAllTopic();
        return  ArticleResponse.builder().body(topicResponse)
                .articleMsgHeader(new ArticleMsgHeader())
                .message(ArticleConst.ARTICLE_TOPIC_ADDED_SUCCESSFUL_NARRATION)
                .httpStatus(200)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }

}

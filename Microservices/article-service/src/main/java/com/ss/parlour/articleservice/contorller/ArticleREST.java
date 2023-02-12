package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/article",consumes= MediaType.APPLICATION_JSON_VALUE)
public class ArticleREST {

    @Autowired
    private ArticleServiceI articleServiceI;

    @RequestMapping(value = "/createArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> createArticle(@RequestBody ArticleCreateRequestBean articleCreateRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.createArticle(articleCreateRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addComment(@RequestBody CommentCreateRequestBean commentCreateRequestBean){
        CommentCommonResponseBean commentCommonResponseBean = articleServiceI.createComment(commentCreateRequestBean);
        return ResponseEntity.ok().body(commentCommonResponseBean);
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addLike(@RequestBody LikeRequestBean likeRequestBean){
        LikeCommonResponseBean likeCommonResponseBean = articleServiceI.addLike(likeRequestBean);
        return ResponseEntity.ok().body(likeCommonResponseBean);
    }

    @RequestMapping(value = "/findArticleById", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findArticleById(@RequestBody ArticleRequestBean articleRequestBean){
        ArticleResponseBean articleResponseBean = articleServiceI.findArticleById(articleRequestBean);
        return ResponseEntity.ok().body(articleResponseBean);
    }

    @RequestMapping(value = "/findArticleHistoryById", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findArticleHistoryById(@RequestBody ArticleHistoryRequestBean articleHistoryRequestBean){
        ArticleHistoryResponseBean articleHistoryResponseBean = articleServiceI.findArticleHistoryById(articleHistoryRequestBean);
        return ResponseEntity.ok().body(articleHistoryResponseBean);
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleDeleteRequestBean articleDeleteRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.deleteArticle(articleDeleteRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteComment(@RequestBody CommentDeleteRequestBean commentDeleteRequestBean){
        CommentCommonResponseBean commentCommonResponseBean = articleServiceI.deleteComment(commentDeleteRequestBean);;
        return ResponseEntity.ok().body(commentCommonResponseBean);
    }

    @RequestMapping(value = "/findArticleDetailsById/{id}", method = RequestMethod.GET, consumes = {"application/json"})
    public Article findArticleDetailsById(@PathVariable("id") String articleId){
        return articleServiceI.findArticleDetailsById(articleId);
    }

    @RequestMapping(value = "/createArticleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ArticleEditRequestResponse createArticleEditRequest(@RequestBody ArticleEditRequest articleEditRequest){
        return articleServiceI.createArticleEditRequest(articleEditRequest);
    }

    @RequestMapping(value = "/approveArticleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ArticleEditApproveResponse approveArticleEditRequest(@RequestBody ArticleEditApproveRequest articleEditApproveRequest){
        return articleServiceI.approveArticleEditRequest(articleEditApproveRequest);
    }

}

package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.createComment(commentCreateRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addLike(@RequestBody LikeRequestBean likeRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.addLike(likeRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/articleById", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findArticleById(@RequestBody ArticleRequestBean articleRequestBean){
        ArticleResponseBean articleResponseBean = articleServiceI.findArticleById(articleRequestBean);
        return ResponseEntity.ok().body(articleResponseBean);
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleDeleteRequestBean articleDeleteRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.deleteArticle(articleDeleteRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteComment(@RequestBody CommentDeleteRequestBean commentDeleteRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = null;
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

}

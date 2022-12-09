package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.LikeRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleCommonResponseBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentRequestBean;
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
    public ResponseEntity<Object> createArticle(@RequestBody ArticleRequestBean articleRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.createArticle(articleRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addComment(@RequestBody CommentRequestBean commentRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.createComment(commentRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addLike(@RequestBody LikeRequestBean likeRequestBean){
        ArticleCommonResponseBean articleCommonResponseBean = articleServiceI.addLike(likeRequestBean);
        return ResponseEntity.ok().body(articleCommonResponseBean);
    }

}

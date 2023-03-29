package com.ss.parlour.articleservice.contorller;

import com.ss.parlour.articleservice.service.ArticleServiceI;
import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import com.ss.parlour.articleservice.utils.bean.requests.*;
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
    public ResponseEntity<?> createArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        ArticleResponse articleResponse = articleServiceI.createArticle(articleCreateRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addComment(@RequestBody CommentCreateRequest commentCreateRequest){
        ArticleResponse articleResponse = articleServiceI.addComment(commentCreateRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addLike(@RequestBody LikeRequest likeRequest){
        ArticleResponse articleResponse = articleServiceI.addLike(likeRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleById", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findArticleById(@RequestParam("articleId") String articleId){
        ArticleResponse articleResponse = articleServiceI.findArticleById(articleId);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleByUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findArticleByUser(@RequestBody ArticleListRequest articleListRequest){
        ArticleResponse articleResponse = articleServiceI.findArticleByUser(articleListRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleHistoryById", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findArticleHistoryById(@RequestBody ArticleHistoryRequest articleHistoryRequest){
        ArticleResponse articleResponse = articleServiceI.findArticleHistoryById(articleHistoryRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleComments", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findArticleComments(@RequestBody CommentRequest commentRequest){
        ArticleResponse articleResponse = articleServiceI.findArticleComments(commentRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findArticleComments(@RequestBody ArticleDeleteRequest articleDeleteRequest){
        ArticleResponse articleResponse = articleServiceI.deleteArticle(articleDeleteRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest){
        ArticleResponse articleResponse = articleServiceI.deleteComment(commentDeleteRequest);;
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleDetailsById/{id}", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findArticleDetailsById(@PathVariable("id") String articleId){
        ArticleResponse articleResponse = articleServiceI.findArticleDetailsById(articleId);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/articleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> articleEditRequest(@RequestBody ArticleEditRequest articleEditRequest){
        ArticleResponse articleResponse = articleServiceI.articleEditRequest(articleEditRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/approveArticleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> approveArticleEditRequest(@RequestBody ArticleEditApproveRequest articleEditApproveRequest){
        ArticleResponse articleResponse = articleServiceI.approveArticleEditRequest(articleEditApproveRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findShareArticles", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findShareArticles(@RequestBody ArticleEditApproveRequest articleEditApproveRequest){
        ArticleResponse articleResponse = articleServiceI.approveArticleEditRequest(articleEditApproveRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/postArticleEditDraft", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> postArticleEditDraft(@RequestBody ArticleEditDraftRequest articleEditDraftRequest){
        ArticleResponse articleResponse = articleServiceI.postArticleEditDraft(articleEditDraftRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> addTopic(@RequestBody TopicAddRequest topicAddRequest){
        ArticleResponse articleResponse = articleServiceI.addTopic(topicAddRequest);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findAllTopic", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<?> findAllTopic(){
        ArticleResponse articleResponse = articleServiceI.findAllTopic();
        return ResponseEntity.ok().body(articleResponse);
    }

}

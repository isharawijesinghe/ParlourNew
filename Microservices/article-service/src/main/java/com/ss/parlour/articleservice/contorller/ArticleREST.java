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
    public ResponseEntity<Object> createArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        ArticleCommonResponse articleCommonResponse = articleServiceI.createArticle(articleCreateRequest);
        return ResponseEntity.ok().body(articleCommonResponse);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addComment(@RequestBody CommentCreateRequest commentCreateRequest){
        CommentCommonResponse commentCommonResponse = articleServiceI.addComment(commentCreateRequest);
        return ResponseEntity.ok().body(commentCommonResponse);
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addLike(@RequestBody LikeRequest likeRequest){
        LikeCommonResponse likeCommonResponse = articleServiceI.addLike(likeRequest);
        return ResponseEntity.ok().body(likeCommonResponse);
    }

    @RequestMapping(value = "/findArticleById", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<Object> findArticleById(@RequestParam("articleId") String articleId){
        ArticleResponse articleResponse = articleServiceI.findArticleById(articleId);
        return ResponseEntity.ok().body(articleResponse);
    }

    @RequestMapping(value = "/findArticleByUser", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> findArticleByUser(@RequestBody ArticleListRequest articleListRequest){
        ArticleListResponse articleListResponse = articleServiceI.findArticleByUser(articleListRequest);
        return ResponseEntity.ok().body(articleListResponse);
    }

    @RequestMapping(value = "/findArticleHistoryById", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findArticleHistoryById(@RequestBody ArticleHistoryRequest articleHistoryRequest){
        ArticleHistoryResponse articleHistoryResponse = articleServiceI.findArticleHistoryById(articleHistoryRequest);
        return ResponseEntity.ok().body(articleHistoryResponse);
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleDeleteRequest articleDeleteRequest){
        ArticleCommonResponse articleCommonResponse = articleServiceI.deleteArticle(articleDeleteRequest);
        return ResponseEntity.ok().body(articleCommonResponse);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest){
        CommentCommonResponse commentCommonResponse = articleServiceI.deleteComment(commentDeleteRequest);;
        return ResponseEntity.ok().body(commentCommonResponse);
    }

    @RequestMapping(value = "/findArticleDetailsById/{id}", method = RequestMethod.GET, consumes = {"application/json"})
    public Article findArticleDetailsById(@PathVariable("id") String articleId){
        return articleServiceI.findArticleDetailsById(articleId);
    }

    @RequestMapping(value = "/articleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> articleEditRequest(@RequestBody ArticleEditRequest articleEditRequest){
        ArticleEditRequestResponse articleEditRequestResponse = articleServiceI.articleEditRequest(articleEditRequest);
        return ResponseEntity.ok().body(articleEditRequestResponse);
    }

    @RequestMapping(value = "/approveArticleEditRequest", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> approveArticleEditRequest(@RequestBody ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = articleServiceI.approveArticleEditRequest(articleEditApproveRequest);
        return ResponseEntity.ok().body(articleEditApproveResponse);
    }

    @RequestMapping(value = "/findShareArticles", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> findShareArticles(@RequestBody ArticleEditApproveRequest articleEditApproveRequest){
        ArticleEditApproveResponse articleEditApproveResponse = articleServiceI.approveArticleEditRequest(articleEditApproveRequest);
        return ResponseEntity.ok().body(articleEditApproveResponse);
    }

    @RequestMapping(value = "/postArticleEditDraft", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> postArticleEditDraft(@RequestBody ArticleEditDraftRequest articleEditDraftRequest){
        ArticleEditDraftResponse articleEditApproveResponse = articleServiceI.postArticleEditDraft(articleEditDraftRequest);
        return ResponseEntity.ok().body(articleEditApproveResponse);
    }

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> addTopic(@RequestBody TopicAddRequest topicAddRequest){
        TopicAddResponse topicAddResponse = articleServiceI.addTopic(topicAddRequest);
        return ResponseEntity.ok().body(topicAddResponse);
    }

    @RequestMapping(value = "/findAllTopic", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<Object> findAllTopic(){
        TopicResponse topicAddResponse = articleServiceI.findAllTopic();
        return ResponseEntity.ok().body(topicAddResponse);
    }

}

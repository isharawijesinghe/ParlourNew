package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.dao.CommentDAOI;
import com.ss.parlour.articleservice.dao.LikeDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class CommentTypeHandler implements CommentHandlerI, LikeTypeHandlerI {

    @Autowired
    private CommentDAOI commentDAOI;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private LikeDAOI likeDAOI;

    //Flow of handle all article comment mechanism
    @Override
    public void handleCommentRequest(CommentBean commentBean){
        Comment comment = populateComment(commentBean);
        handleComment(comment);
        handleCommentByArticle(comment);
    }

    @Override
    public void handleLikeType(Like like){
        HashMap<String, Like> likeMap = new HashMap<>();
        Optional<LikeByComment> existingLikeByCommentBean = commentDAOI.getLikeByComment(like.getCommentId(), like.getArticleId());
        if (existingLikeByCommentBean.isPresent()){
            likeMap =  existingLikeByCommentBean.get().getLikeMap();

        }
        likeMap.put(like.getKey(), like);
        LikeByArticle likeByArticle = new LikeByArticle();
        likeByArticle.setArticleId(like.getArticleId());
        likeByArticle.setLikeMap(likeMap);
    }

    //Responsible in handling comment persisting
    // 1. Validate existing of comment
    // 2. If it already exists then update and add previous to history
    // 3. If it not exists create new comment
    protected void handleComment(Comment comment){
        Optional<Comment> existingComment = commentDAOI.getCommentById(comment.getId());
        if (existingComment.isPresent()){
            //Comment update flow --> Adding to history etc
        }
        createComment(comment);
    }

    //Handle comment list by article id
    protected void handleCommentByArticle(Comment comment){
        Optional<CommentByArticle> existingCommentByArticle = commentDAOI.getCommentsByArticleId(comment.getArticleId());
        List<Comment> commentList ;
        //Process when comments are available for articles
        //--> Adding old one to history
        //-->Add audit trail
        if (existingCommentByArticle.isPresent()){
            commentList = existingCommentByArticle.get().getComments();
            commentList.add(comment);
        } else {
            //Process when comments not available for articles
            commentList = new ArrayList<>();
            commentList.add(comment);
        }
        CommentByArticle commentByArticle = new CommentByArticle();
        commentByArticle.setArticleId(comment.getArticleId());
        commentByArticle.getComments().addAll(commentList);
        commentDAOI.saveCommentByArticle(commentByArticle);
    }

    //Insert or update article
    protected void createComment(Comment comment){
        commentDAOI.saveComment(comment);
    }

    protected Comment populateComment(CommentBean commentBean){
        Comment comment = new Comment();
        comment.setId(commentBean.getId());
        comment.setArticleId(commentBean.getArticleId());
        comment.setParentId(commentBean.getParentId());
        comment.setUserName(commentBean.getAuthorName());
        comment.setContent(commentBean.getContent());
        comment.setCreatedDate(commentBean.getCreatedDate());
        comment.setModifiedDate(commentBean.getModifiedDate());
        return comment;
    }

}

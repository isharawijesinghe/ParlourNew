package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.dao.CommentDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommentHandler implements CommentHandlerI, LikeTypeHandlerI {

    @Autowired
    private CommentDAOI commentDAOI;

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

    public void deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        Optional<Comment> existingCommentBean = commentDAOI.getCommentById(commentDeleteRequestBean.getCommentId());
        if (existingCommentBean.isPresent()){

            //Update comment bean in db --> does require to remove from db ???
            Comment oldComment = existingCommentBean.get();
            oldComment.setStatus(ArticleConst.COMMENT_INACTIVE);
            commentDAOI.saveComment(oldComment);

            //Update article assign comment map in db
            removeArticleAssignComment(oldComment);

            //Update comment history in db
        }
    }

    @Override
    public List<Comment> getCommentListForPost(ArticleRequestBean articleRequestBean){
        Optional<CommentByArticle> existingCommentByArticle = commentDAOI.getCommentsByArticleId(articleRequestBean.getArticleId());
        List<Comment> responseComment = new ArrayList<>();
        if (existingCommentByArticle.isPresent()){
            CommentByArticle commentByArticleExisting = existingCommentByArticle.get();
            //Load existing parent comment assign child comment list
            HashMap<String, ArrayList<Comment>> parentToChildCommentMap = commentByArticleExisting.getComments();
            //Stream over parent child comment map to get list of comments
            List<Comment> listOfComments = parentToChildCommentMap
                                           .values()
                                           .stream()
                                           .flatMap(Collection::stream)
                                           .collect(Collectors.toList());

            //Loop over each comment and assign child comment list into that
            listOfComments.forEach(
                    comment -> {
                        List<Comment> childComment = parentToChildCommentMap.get(comment.getId());
                        if (childComment != null){
                            comment.setSubComments(childComment);
                        }
                    }
            );
            //Return top level comments from the list --> This includes all child entries as well
            return parentToChildCommentMap.get(ArticleConst.NO_PARENT_COMMENT);
        }
        return responseComment;
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
        HashMap<String, ArrayList<Comment>> commentMap = new HashMap<>();
        //Process when comments are available for articles >> Adding old one to history / Add audit trail
        if (existingCommentByArticle.isPresent()){
            HashMap<String, ArrayList<Comment>> existingCommentMap = existingCommentByArticle.get().getComments();
            commentMap = existingCommentMap;
        }

        //Update comment map (by article) based on parent id
        if (!commentMap.containsKey(comment.getParentId())){
            commentMap.put(comment.getParentId(), new ArrayList<>());
        }
        commentMap.get(comment.getParentId()).add(comment);

        //Update comment by article values into db
        updatedCommentByArticle(comment.getArticleId(), commentMap);
    }

    //This will remove article assign comment from stores map when comment deleted
    protected void removeArticleAssignComment(Comment oldComment){
        Optional<CommentByArticle> existingCommentByArticleBean = commentDAOI.getCommentsByArticleId(oldComment.getArticleId());
        if (existingCommentByArticleBean.isPresent()){
            HashMap<String, ArrayList<Comment>> currentCommentMap = existingCommentByArticleBean.get().getComments();

            //If comment itself is parent then remove it, and it's child from map
            String commentId = oldComment.getId();
            currentCommentMap.remove(commentId);

            //If comment is child of parent then remove it from child list
            String parentCommentId = oldComment.getParentId();
            List<Comment> childCommentList = currentCommentMap.get(parentCommentId);
            childCommentList.removeIf(childComment -> childComment.getId() == oldComment.getId());

            //Update comment by article values into db
            updatedCommentByArticle(oldComment.getArticleId(), currentCommentMap);
        }
    }

    protected void updatedCommentByArticle(String articleId, HashMap<String, ArrayList<Comment>> commentMap){
        CommentByArticle commentByArticle = new CommentByArticle();
        commentByArticle.setArticleId(articleId);
        commentByArticle.setComments(commentMap);
        commentDAOI.saveCommentByArticle(commentByArticle);
    }

    //Insert or update article
    protected void createComment(Comment comment){
        commentDAOI.saveComment(comment);
    }

    //Populate and create comment entry in db
    protected Comment populateComment(CommentBean commentBean){
        Comment comment = new Comment();
        comment.setId(commentBean.getId());
        comment.setArticleId(commentBean.getArticleId());
        comment.setParentId(commentBean.getParentId());
        comment.setUserName(commentBean.getAuthorName());
        comment.setContent(commentBean.getContent());
        comment.setCreatedDate(commentBean.getCreatedDate());
        comment.setModifiedDate(commentBean.getModifiedDate());
        comment.setSubComments(Arrays.asList(comment));
        return comment;
    }

}

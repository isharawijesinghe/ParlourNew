package com.ss.parlour.articleservice.handler.comment;

import com.ss.parlour.articleservice.dao.cassandra.CommentDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.CommentBean;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.CommentDeleteRequestBean;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommentHandler implements CommentHandlerI, LikeTypeHandlerI {

    @Autowired
    private CommentDAOI commentDAOI;

    @Autowired
    private KeyGenerator keyGenerator;


    //When user add comment on article
    @Override
    public Comment handleCommentRequest(CommentBean commentBean){
        if(commentBean.getId() == null || commentBean.getId().isEmpty()){
            //Adding new comment id if request do not have comment id >> This is for new comment
            commentBean.setId(keyGenerator.commentKeyGenerator(commentBean.getAuthorName(), commentBean.getArticleId()));
        }
        Comment comment = populateComment(commentBean);
        handleComment(comment);
        handleCommentByArticle(comment);
        return comment;
    }

    //When user vote for comment
    @Override
    public void handleLikeRequest(LikeBean likeBean){
        Optional<Comment> existingCommentBean = commentDAOI.getCommentById(likeBean.getCommentId());
        if (existingCommentBean.isPresent()){
            //Update saved comment object in db >> Update "Comment" table
            Comment updatedComment = updateCommentVote(likeBean, existingCommentBean.get());

            //Update article assign comment map in db >> Update "CommentByArticle" table
            updatedArticleAssignComment(updatedComment);

        }
    }

    //When delete comment
    @Override
    public void deleteComment(CommentDeleteRequestBean commentDeleteRequestBean){
        Optional<Comment> existingCommentBean = commentDAOI.getCommentById(commentDeleteRequestBean.getCommentId());
        if (existingCommentBean.isPresent()){

            //Update comment bean in db --> does require to remove from db ???
            Comment oldComment = existingCommentBean.get();
            oldComment.setStatus(ArticleConst.COMMENT_INACTIVE);
            commentDAOI.saveComment(oldComment);

            //Update article assign comment map in db
            removeArticleAssignComment(oldComment);
        }
    }

    //When user request for comment list for post
    @Override
    public List<Comment> getCommentListForPost(ArticleRequestBean articleRequestBean){
        //Load all comments for particular article
        Optional<CommentByArticle> existingCommentByArticle = commentDAOI.getCommentsByArticleId(articleRequestBean.getArticleId());
        List<Comment> responseComment = new ArrayList<>();
        if (existingCommentByArticle.isPresent()){

            CommentByArticle commentByArticleExisting = existingCommentByArticle.get();
            //Load existing parent comment assign child comment list
            HashMap<String, List<Comment>> parentToChildCommentMap = commentByArticleExisting.getComments();
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
        HashMap<String, List<Comment>> commentMap = new HashMap<>();

        //Process when comments are available for articles >> Adding old one to history / Add audit trail
        if (existingCommentByArticle.isPresent()){
            HashMap<String, List<Comment>> existingCommentMap = existingCommentByArticle.get().getComments();
            commentMap = existingCommentMap;
        }

        //Update comment map (by article) based on parent id
        if (!commentMap.containsKey(comment.getParentId())){
            commentMap.put(comment.getParentId(), new ArrayList<>());
        }
        commentMap.get(comment.getParentId()).add(comment);

        //Update comment by article values into db >> save in db
        updatedCommentByArticle(comment.getArticleId(), commentMap);
    }

    //This will remove article assign comment from stores map when comment deleted
    protected void removeArticleAssignComment(Comment oldComment){
        Optional<CommentByArticle> existingCommentByArticleBean = commentDAOI.getCommentsByArticleId(oldComment.getArticleId());
        if (existingCommentByArticleBean.isPresent()){
            HashMap<String, List<Comment>> currentCommentMap = existingCommentByArticleBean.get().getComments();

            //If comment itself is parent then remove it, and it's child from map
            String commentId = oldComment.getId();
            currentCommentMap.remove(commentId);

            //If comment is child of parent then remove it from child list
            String parentCommentId = oldComment.getParentId();
            List<Comment> childCommentList = currentCommentMap.get(parentCommentId);
            childCommentList.removeIf(childComment -> childComment.getId().equals(oldComment.getId()));

            //Update comment by article values into db
            updatedCommentByArticle(oldComment.getArticleId(), currentCommentMap);
        }
    }

    //Update comment like / unlike status
    protected Comment updateCommentVote(LikeBean likeBean, Comment oldComment){
        Set<String> likedUsers = oldComment.getLikedList();
        Set<String> unLikeUsers = oldComment.getUnLikedList();

        //Update user like or unlike list based on user input
        switch (likeBean.getStatus()){
            case ArticleConst.USER_LIKED:
                likedUsers.add(likeBean.getUserName());
                unLikeUsers.removeIf(username -> username.equals(likeBean.getUserName()));
                break;
            case ArticleConst.USER_UNLIKED:
                unLikeUsers.add(likeBean.getUserName());
                likedUsers.removeIf(username -> username.equals(likeBean.getUserName()));
                break;
            default:
                likedUsers.removeIf(username -> username.equals(likeBean.getUserName()));
                unLikeUsers.removeIf(username -> username.equals(likeBean.getUserName()));
                break;
        }

        Comment updatedComment = oldComment;
        updatedComment.setLikedList(likedUsers);
        updatedComment.setUnLikedList(unLikeUsers);
        commentDAOI.saveComment(updatedComment);

        return updatedComment;
    }

    //Update comment by article bean to db
    protected void updatedArticleAssignComment(Comment updatedComment){
        Optional<CommentByArticle> existingCommentByArticleBean = commentDAOI.getCommentsByArticleId(updatedComment.getArticleId());
        if (existingCommentByArticleBean.isPresent()){
            //Load current child map list
            HashMap<String, List<Comment>> currentCommentMap = existingCommentByArticleBean.get().getComments();
            List<Comment> childCommentList = currentCommentMap.get(updatedComment.getParentId());

            List<Comment> newChildCommentList = childCommentList.parallelStream()
                    .map(comment -> comment.getId().equals(updatedComment.getId()) ? updatedComment : comment)
                    .collect(Collectors.toList());
            currentCommentMap.put(updatedComment.getParentId(), newChildCommentList);

            //Update comment by article values into db
            updatedCommentByArticle(updatedComment.getArticleId(), currentCommentMap);
        }
    }

    //Update comment by article bean to db
    protected void updatedCommentByArticle(String articleId, HashMap<String, List<Comment>> commentMap){
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

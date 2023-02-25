package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.*;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;
import com.ss.parlour.articleservice.utils.common.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Autowired
    private KeyGenerator keyGenerator;

    //Handle when user request post create
    @Override
    public Article processCreateArticleRequest(ArticleBean articleBean){
        if (articleBean.getId() == null || articleBean.getId().isEmpty()){ //New article request
            articleBean.setId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        } else { //Article update approve request flow
            Optional<Article> currentArticle = articleDAOI.getArticleById(articleBean.getId());
            currentArticle.ifPresent(article -> updateArticle(article)); //Update ArticleHistory table
        }
        Article article = populateArticle(articleBean); //Populate article bean
        updateArticle(article); //Create or update article >> Update Article table
        return article;
    }

    //Handle when user add post like
    @Override
    public void handleLikeRequest(LikeBean likeBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(likeBean.getArticleId());
        //Update saved article object in db >> Update Article table
        existingArticleBean.ifPresent((article) -> updateArticleVote(likeBean, article));
    }

    //Handle when user delete an article
    @Override
    public void processDeleteArticleRequest(ArticleDeleteRequestBean articleDeleteRequestBean){
        Optional<Article> exitingArticle = articleDAOI.getArticleById(articleDeleteRequestBean.getArticleId());
        //If delete request article exists --> Then update article status to inactive
        //If required this can be deleted
        exitingArticle.ifPresent((article) -> processUpdateArticleStatus(article, ArticleConst.ARTICLE_INACTIVE));
    }

    //When user request to find article by id
    @Override
    public ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean){
        ArticleResponseBean articleResponseBean = new ArticleResponseBean();
        populateArticleDetails(articleRequestBean, articleResponseBean);
        articleResponseBean.setArticleComments(commentHandlerI.getCommentListForPost(articleRequestBean));
        return articleResponseBean;
    }

    //When user request to find article history by id
    @Override
    public ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean){
        ArticleHistoryResponseBean articleHistoryResponseBean = new ArticleHistoryResponseBean();
        Optional<ArticleHistory> currentArticleHistory = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequestBean.getArticleId());
        currentArticleHistory.ifPresent((articleHistory -> articleHistoryResponseBean.setArticleHistoryList(articleHistory.getOldArticles())));
        return articleHistoryResponseBean;
    }

    //Find user article by id
    @Override
    public Article findArticleDetailsById(String articleId){
        Optional<Article> currentArticle = articleDAOI.getArticleById(articleId);
        currentArticle.ifPresent(article -> returnArticle(article));
        return null;
    }

    //Handle article edit request
    @Override
    public String processArticleEditRequest(ArticleEditRequestBean articleEditRequestBean){
        ArticleEditBean articleEditBean = articleEditRequestBean.getArticleEditBean();
        String articleEditRequestId = keyGenerator.articleEditRequestGenerator(articleEditBean.getArticleId(), articleEditBean.getRequesterId());
        articleEditBean.setEditRequestId(articleEditRequestId); //Update article edit request id (~Generate)
        //Process article edit request (update article edit request + article edit request for user)
        processEditRequestForArticle(articleEditBean, ArticleConst.ARTICLE_EDIT_REQUEST_PENDING);
        return articleEditRequestId;
    }

    //Call article edit approve request
    //Once article edit approve it should updates article edit beans + insert data into shared article table
    @Override
    public void processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest){
        processEditApproveRequestForArticle(articleEditApproveRequest, ArticleConst.ARTICLE_EDIT_REQUEST_APPROVED);
    }

    //---------------------------********* Article handler provide methods *********---------------------------//

    protected void processEditApproveRequestForArticle(ArticleEditApproveRequest articleEditApproveRequest, String status){
        updateArticleEditRequestStatus(articleEditApproveRequest,status);
        updateArticleEditRequestForUserStatus(articleEditApproveRequest,status);
        updateSharedArticleEntry(articleEditApproveRequest, status);
    }

    protected void processEditRequestForArticle(ArticleEditBean articleEditBean, String status){
        ArticleEditBean updatedArticleEditBean =  populateArticleEditBean(articleEditBean, status);
        updateArticleEditRequest(updatedArticleEditBean);//Add article edit request to database against article id
        updateArticleEditRequestForUser(updatedArticleEditBean);//Add article edit request to database against user id
    }

    protected Article returnArticle(Article article){
        return article;
    }

    //Update article status
    protected void processUpdateArticleStatus(Article currentArticle, int status){
        currentArticle.setStatus(status);
        articleDAOI.saveArticle(currentArticle);
    }

    //Update article like / unlike status
    protected void updateArticleVote(LikeBean likeBean, Article oldArticle){
        Set<String> likedUsers = oldArticle.getLikedList();
        Set<String> unLikeUsers = oldArticle.getUnLikedList();

        switch (likeBean.getStatus()){ //Update user like or unlike list based on user input
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

        Article updatedArticle = oldArticle;
        updatedArticle.setLikedList(likedUsers);
        updatedArticle.setUnLikedList(unLikeUsers);
        articleDAOI.saveArticle(updatedArticle);
    }

    //Populate article details to send response back to browser
    protected void populateArticleDetails(ArticleRequestBean articleRequestBean, ArticleResponseBean articleResponseBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(articleRequestBean.getArticleId());
        //Only load if article exists and active
        if (existingArticleBean.isPresent() && existingArticleBean.get().getStatus() == ArticleConst.ARTICLE_ACTIVE){
            Article article = existingArticleBean.get();
            articleResponseBean.setArticle(article);
        }
    }

    //Update article >> Update or save
    protected void updateArticle(Article article){
        articleDAOI.saveArticle(article);
    }

    //Populate Article bean
    protected Article populateArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setUserName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        article.setStatus(ArticleConst.ARTICLE_ACTIVE);
        return article;
    }

    //Update article history into db
    protected void updateArticleHistory(Article oldArticle){
        Optional<ArticleHistory> existingArticleHistoryBean = articleDAOI.getArticleHistoryByArticleId(oldArticle.getId());
        List<Article> articleHistoriesList = new ArrayList<>();
        if (existingArticleHistoryBean.isPresent()){
            articleHistoriesList = existingArticleHistoryBean.get().getOldArticles();
        }
        articleHistoriesList.add(oldArticle);
        ArticleHistory articleHistory = new ArticleHistory();
        articleHistory.setArticleId(existingArticleHistoryBean.get().getArticleId());
        articleHistory.setOldArticles(articleHistoriesList);
        articleDAOI.updateArticleHistory(articleHistory);
    }

    protected void updateArticleEditRequestStatus(ArticleEditApproveRequest articleEditApproveRequest, String status){
        Optional<ArticleEditRequest> currentEditRequest = articleDAOI.getArticleEditRequestForArticleId(articleEditApproveRequest.getArticleId());
        currentEditRequest.ifPresent(articleEditRequest -> {currentEditRequest.get().getArticleEditBeanMap()
                .get(articleEditApproveRequest.getEditRequestId())
                .setEditRequestStatus(status);
        });
        //Updated db with modified entity
        articleDAOI.saveArticleEditRequest(currentEditRequest.get());
    }

    protected void updateArticleEditRequestForUserStatus(ArticleEditApproveRequest articleEditApproveRequest, String status){
        Optional<ArticleEditRequestForUser> currentEditRequestForUser = articleDAOI.getArticleEditRequestForUserId(articleEditApproveRequest.getOwnerId());
        currentEditRequestForUser.ifPresent(articleEditRequestForUser -> {currentEditRequestForUser.get().getArticleEditBeanMapForUser()
                .get(articleEditApproveRequest.getEditRequestId())
                .setEditRequestStatus(status);
        });
        //Updated db with modified entity
        articleDAOI.saveArticleEditRequestForUser(currentEditRequestForUser.get());
    }

    protected void updateSharedArticleEntry(ArticleEditApproveRequest articleEditApproveRequest, String status){
        Optional<SharedArticles> currentShareArticle = articleDAOI.getSharedArticlesForUserId(articleEditApproveRequest.getOwnerId());
        SharedArticleBean sharedArticleBean = populateShareArticleBean(articleEditApproveRequest, status);
        SharedArticles sharedArticles = new SharedArticles();
        sharedArticles.setUserId(articleEditApproveRequest.getOwnerId());
        if (currentShareArticle.isPresent()){
            sharedArticles = currentShareArticle.get();
        }
        sharedArticles.getSharedArticleBeanMap().put(articleEditApproveRequest.getEditRequestId(), sharedArticleBean);
        articleDAOI.saveSharedArticles(sharedArticles);
    }

    protected SharedArticleBean populateShareArticleBean(ArticleEditApproveRequest articleEditApproveRequest, String status){
        SharedArticleBean sharedArticleBean = new SharedArticleBean();
        sharedArticleBean.setArticleId(articleEditApproveRequest.getArticleId());
        sharedArticleBean.setStatus(status);
        sharedArticleBean.setAuthor(articleEditApproveRequest.getOwnerId());
        return sharedArticleBean;
    }

    protected void updateArticleEditRequest(ArticleEditBean articleEditBean){
        Optional<ArticleEditRequest> currentEditRequest = articleDAOI.getArticleEditRequestForArticleId(articleEditBean.getArticleId());
        ArticleEditRequest articleEditRequestBean = new ArticleEditRequest();
        articleEditRequestBean.setArticleId(articleEditBean.getArticleId());
        if (currentEditRequest.isPresent()){
            articleEditRequestBean = currentEditRequest.get();
        }
        articleEditRequestBean.getArticleEditBeanMap().put(articleEditBean.getEditRequestId(), articleEditBean);
        articleDAOI.saveArticleEditRequest(articleEditRequestBean);
    }

    protected void updateArticleEditRequestForUser(ArticleEditBean articleEditBean){
        Optional<ArticleEditRequestForUser> currentEditRequest = articleDAOI.getArticleEditRequestForUserId(articleEditBean.getOwnerId());
        ArticleEditRequestForUser articleEditRequestBeanForUser = new ArticleEditRequestForUser();
        articleEditRequestBeanForUser.setUserId(articleEditBean.getOwnerId());
        if (currentEditRequest.isPresent()){
            articleEditRequestBeanForUser = currentEditRequest.get();
        }
        articleEditRequestBeanForUser.getArticleEditBeanMapForUser().put(articleEditBean.getEditRequestId(), articleEditBean);
        articleDAOI.saveArticleEditRequestForUser(articleEditRequestBeanForUser);
    }

    protected ArticleEditBean populateArticleEditBean(ArticleEditBean articleEditBean, String status){
        articleEditBean.setEditRequestStatus(status);
        return articleEditBean;
    }


}

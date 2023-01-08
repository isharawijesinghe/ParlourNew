package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.cassandra.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.LikeBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleDeleteRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleHistoryRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
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

    //When user post an article
    @Override
    public Article handleArticleRequest(ArticleBean articleBean){
        if (articleBean.getId() == null || articleBean.getId().isEmpty()){ //New article request
            articleBean.setId(keyGenerator.articleKeyGenerator(articleBean.getAuthorName()));
        } else { //Article update request flow
            Optional<Article> existingArticleBean = articleDAOI.getArticleById(articleBean.getId());
            if (existingArticleBean.isPresent()){ //Article update flow
                Article oldArticle = existingArticleBean.get();
                //Update "ArticleHistory" table
                updateArticleHistory(oldArticle);
            }
        }
        Article article = populateArticle(articleBean);
        //Create or update article >> Update "Article" table
        updateArticle(article);
        return article;
    }

    //When user like on article
    @Override
    public void handleLikeRequest(LikeBean likeBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(likeBean.getArticleId());
        if (existingArticleBean.isPresent()){
            //Update saved article object in db >> Update "Article" table
            updateArticleVote(likeBean, existingArticleBean.get());
        }
    }

    //When user delete an article
    @Override
    public void deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean){
        Optional<Article> exitingArticle = articleDAOI.getArticleById(articleDeleteRequestBean.getArticleId());
        if (exitingArticle.isPresent()){
            //This update article status >> This can be removed from db if required
            Article existingArticle = exitingArticle.get();
            existingArticle.setStatus(ArticleConst.ARTICLE_INACTIVE);
            articleDAOI.saveArticle(existingArticle);
        }
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
        Optional<ArticleHistory> existingArticleHistoryBean = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequestBean.getArticleId());
        if (existingArticleHistoryBean.isPresent()){
            articleHistoryResponseBean.setArticleHistoryList(existingArticleHistoryBean.get().getOldArticles());
        }
        return articleHistoryResponseBean;
    }

    @Override
    public Article findArticleDetailsById(String articleId){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(articleId);
        if (existingArticleBean.isPresent()){
            return existingArticleBean.get();
        }
        return null;
    }

    //Update article like / unlike status
    protected void updateArticleVote(LikeBean likeBean, Article oldArticle){
        Set<String> likedUsers = oldArticle.getLikedList();
        Set<String> unLikeUsers = oldArticle.getUnLikedList();

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

}

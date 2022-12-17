package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.ArticleHistory;
import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleDeleteRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleHistoryRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleHistoryResponseBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Override
    public void handleArticleRequest(ArticleBean articleBean){
        Optional<Article> existingArticleBean = articleDAOI.getArticleById(articleBean.getId());
        Article article = populateArticle(articleBean);
        if (existingArticleBean.isPresent()){
            //Article update flow >> Adding old one to history / Add audit trail
            Article oldArticle = existingArticleBean.get();
            updateArticleHistory(oldArticle);
        }
        updateArticle(article); //Create or update article
    }

    @Override
    public void handleLikeType(Like like){
        HashMap<String, Like> likeMap = new HashMap<>();
        Optional<LikeByArticle> existingLikeByArticleBean = articleDAOI.getLikeByArticle(like.getArticleId());
        if (existingLikeByArticleBean.isPresent()){
            likeMap =  existingLikeByArticleBean.get().getLikeMap();

        }
        likeMap.put(like.getKey(), like);
        LikeByArticle likeByArticle = new LikeByArticle();
        likeByArticle.setArticleId(like.getArticleId());
        likeByArticle.setLikeMap(likeMap);
    }

    @Override
    public void deleteArticle(ArticleDeleteRequestBean articleDeleteRequestBean){
        Optional<Article> exitingArticle = articleDAOI.getArticleById(articleDeleteRequestBean.getArticleId());
        if (exitingArticle.isPresent()){
            //This update article status --> This can be removed from db if required
            Article existingArticle = exitingArticle.get();
            existingArticle.setStatus(ArticleConst.ARTICLE_INACTIVE);
            articleDAOI.saveArticle(existingArticle);
        }
    }

    @Override
    public ArticleResponseBean findArticleById(ArticleRequestBean articleRequestBean){
        ArticleResponseBean articleResponseBean = new ArticleResponseBean();
        articleResponseBean.setArticleComments(commentHandlerI.getCommentListForPost(articleRequestBean));
        return articleResponseBean;
    }

    @Override
    public ArticleHistoryResponseBean findArticleHistoryById(ArticleHistoryRequestBean articleHistoryRequestBean){
        ArticleHistoryResponseBean articleHistoryResponseBean = new ArticleHistoryResponseBean();
        Optional<ArticleHistory> existingArticleHistoryBean = articleDAOI.getArticleHistoryByArticleId(articleHistoryRequestBean.getArticleId());
        if (existingArticleHistoryBean.isPresent()){
            articleHistoryResponseBean.setArticleHistoryList(existingArticleHistoryBean.get().getOldArticles());
        }
        return articleHistoryResponseBean;
    }

    protected void updateArticle(Article article){
        articleDAOI.saveArticle(article);
    }

    protected Article populateArticle(ArticleBean articleBean){
        Article article = new Article();
        article.setId(articleBean.getId());
        article.setUserName(articleBean.getAuthorName());
        article.setTitle(articleBean.getTitle());
        article.setSummary(articleBean.getSummary());
        article.setContent(articleBean.getContent());
        article.setCreatedDate(articleBean.getCreatedDate());
        article.setModifiedDate(articleBean.getModifiedDate());
        return article;
    }

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

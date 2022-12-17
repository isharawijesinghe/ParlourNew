package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.domain.cassandra.Like;
import com.ss.parlour.articleservice.domain.cassandra.LikeByArticle;
import com.ss.parlour.articleservice.handler.LikeTypeHandlerI;
import com.ss.parlour.articleservice.handler.comment.CommentHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleDeleteRequestBean;
import com.ss.parlour.articleservice.utils.bean.requests.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.response.ArticleResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class ArticleHandler implements ArticleHandlerI, LikeTypeHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private CommentHandlerI commentHandlerI;

    @Override
    public void handleArticleRequest(ArticleBean articleBean){
        Optional<Article> existingArticle = articleDAOI.getArticleById(articleBean.getId());
        Article article = populateArticle(articleBean);
        if (existingArticle.isPresent()){
            //Article update flow
            //--> Adding old one to history
            //-->Add audit trail

        }
        createArticle(article);
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

    protected void createArticle(Article article){
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


}

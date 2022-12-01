package com.ss.parlour.articleservice.handler;

import com.ss.parlour.articleservice.dao.ArticleDAOI;
import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import com.ss.parlour.articleservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.ArticleResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleHandler implements ArticleHandlerI {

    @Autowired
    private ArticleDAOI articleDAOI;

    @Autowired
    private ArticleModifyHandlerI articleModifyHandlerI;

    @Autowired
    private ArticleNewHandlerI articleNewHandlerI;

    @Override
    public ArticleResponseBean handleArticleRequest(ArticleRequestBean articleRequestBean){
        Optional<Article> existingArticle = articleDAOI.getArticleById(articleRequestBean.getId());
        ArticleResponseBean articleResponseBean = null;
        if (existingArticle.isPresent()){
            //Article update flow
            articleResponseBean = new ArticleResponseBean();
        } else {
            //New article create flow
            articleNewHandlerI.createArticle(articleRequestBean);
            articleResponseBean = new ArticleResponseBean(ArticleConst.STATUS_SUCCESS, ArticleConst.SUCCESSFULLY_CREATED_ARTICLE);
        }
        return articleResponseBean;
    }

}

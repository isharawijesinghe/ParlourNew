package com.ss.parlour.mainservice.handler;

import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.utils.bean.ArticleResponseBean;
import com.ss.parlour.mainservice.bean.Const;
import com.ss.parlour.mainservice.domain.Article;
import com.ss.parlour.mainservice.repository.ArticleRepositoryI;
import com.ss.parlour.mainservice.utils.exception.MainServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleHander implements ArticleHandlerI {

    @Autowired
    private ArticleRepositoryI articleRepositoryI;

    @Override
    public ArticleResponseBean create(ArticleRequestBean articleRequestBean) {
        Article article=new Article();
        article.setChannelId(articleRequestBean.getChannelID());
        article.setContent(articleRequestBean.getContent());
        article.setTitle(articleRequestBean.getTitle());
        articleRepositoryI.save(article);
        return new ArticleResponseBean("Article added successfully");
    }

    @Override
    public ArticleResponseBean viewArticles(int channelId) {
        if(channelId<=0){
            throw new MainServiceRuntimeException(Const.ERROR_DES_INVALID_CHANEL_ID);
        }
        List<Article> articles= articleRepositoryI.findByChannelId(channelId);
        ArticleResponseBean articleResponseBean=new ArticleResponseBean();
        articleResponseBean.setArticleList(articles);
        return articleResponseBean;
    }

    @Override
    public ArticleResponseBean viewArticles() {
        List<Article> articles= articleRepositoryI.findAll();
        ArticleResponseBean articleResponseBean=new ArticleResponseBean();
        articleResponseBean.setArticleList(articles);
        return articleResponseBean;
    }

    public ArticleRepositoryI getArticleRepositoryI() {
        return articleRepositoryI;
    }

    public void setArticleRepositoryI(ArticleRepositoryI articleRepositoryI) {
        this.articleRepositoryI = articleRepositoryI;
    }
}

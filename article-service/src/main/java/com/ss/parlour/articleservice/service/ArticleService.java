package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.ArticleHandlerI;
import com.ss.parlour.articleservice.utils.bean.ArticleErrorCodes;
import com.ss.parlour.articleservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.articleservice.utils.bean.ArticleResponseBean;
import com.ss.parlour.articleservice.utils.exception.ArticleServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements ArticleServiceI {

    @Autowired
    private ArticleHandlerI articleHandlerI;

    public ArticleResponseBean createArticle(ArticleRequestBean articleRequestBean){
        try {
            return articleHandlerI.handleArticleRequest(articleRequestBean);
        }catch (ArticleServiceRuntimeException ex){
            throw ex;
        }catch (Exception ex){
            throw new ArticleServiceRuntimeException(ArticleErrorCodes.UNKNOWN_ERROR , ex);
        }
    }
}

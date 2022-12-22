package com.ss.parlour.mainservice.contorller;

import com.ss.parlour.mainservice.utils.bean.ArticleRequestBean;
import com.ss.parlour.mainservice.utils.bean.ArticleResponseBean;
import com.ss.parlour.mainservice.service.ArticleServiceI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/article",consumes= MediaType.APPLICATION_JSON_VALUE)
public class ArticleREST {
    private static Logger logger= LogManager.getLogger("ArticleREST.class");

    @Autowired
    private ArticleServiceI articleServiceI;

    @RequestMapping(value = "/createArticle", method = RequestMethod.POST, consumes = {"application/json"})
    public ArticleResponseBean create(@RequestBody ArticleRequestBean request){
        logger.debug("=Create article request found:"+request);
        return articleServiceI.create(request);
    }

    @RequestMapping(value = "/viewArticlesByChannel", method = RequestMethod.POST, consumes = {"application/json"})
    public ArticleResponseBean viewArticlesByChannel(@RequestBody ArticleRequestBean request){
        logger.debug("=View articles by channel request found:"+request);
        return articleServiceI.viewArticles(request.getChannelID());
    }

    @RequestMapping(value = "/viewAllArticles", method = RequestMethod.POST, consumes = {"application/json"})
    public ArticleResponseBean viewAllArticles(){
        logger.debug("=View all articles request found:");
        return articleServiceI.viewArticles();
    }

    public ArticleServiceI getArticleServiceI() {
        return articleServiceI;
    }

    public void setArticleServiceI(ArticleServiceI articleServiceI) {
        this.articleServiceI = articleServiceI;
    }
}

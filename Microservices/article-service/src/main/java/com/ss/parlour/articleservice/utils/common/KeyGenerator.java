package com.ss.parlour.articleservice.utils.common;

import com.datastax.driver.core.utils.UUIDs;
import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

    public String articleKeyGenerator(String userName){
        return ArticleConst.ARTICLE_TYPE + "_" + userName + "_" +  UUIDs.timeBased();
    }

    public String commentKeyGenerator(String userName, String articleId){
        return ArticleConst.COMMENT_TYPE + "_" +  userName + "_" +  articleId + "_" + UUIDs.timeBased();
    }

    public String likeKeyGenerator(String articleId, String commentId){
        return ArticleConst.LIKE_TYPE + "_" +  articleId + "_" + commentId + "_" + UUIDs.timeBased();
    }

    public String articleEditRequestGenerator(String articleId, String userName){
        return articleId + "_" + userName + "_" +  UUIDs.timeBased();
    }

    public String generateUniqueIdentifier(){
        return String.valueOf(UUIDs.timeBased());
    }
}

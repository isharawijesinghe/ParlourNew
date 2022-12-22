package com.ss.parlour.articleservice.utils.common;

import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

    public String getLikeKey(String articleId, String commentId, String userName){
        return articleId + ArticleConst.SEPARATOR + commentId + ArticleConst.SEPARATOR + userName;
    }
}

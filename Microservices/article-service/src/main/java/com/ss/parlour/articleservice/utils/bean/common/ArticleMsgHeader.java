package com.ss.parlour.articleservice.utils.bean.common;

import com.ss.parlour.articleservice.utils.bean.ArticleConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleMsgHeader {

    private String tenantCode = ArticleConst.DEFAULT_TENANCY_CODE;
    private String userId;
    private String clientIP;
}

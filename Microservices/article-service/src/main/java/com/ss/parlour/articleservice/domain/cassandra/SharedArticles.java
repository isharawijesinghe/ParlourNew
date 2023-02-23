package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.SharedArticleBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table("sharedarticles")
@Getter
@Setter
@NoArgsConstructor
public class SharedArticles {

    private String userId;
    private HashMap<String, SharedArticleBean> sharedArticleBeanMap = new HashMap<>();
}

package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.ArticleEditBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table("editrequestforarticle")
@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequest {

    private String articleId;
    private HashMap<String, ArticleEditBean> articleEditBeanMap = new HashMap<>();
}

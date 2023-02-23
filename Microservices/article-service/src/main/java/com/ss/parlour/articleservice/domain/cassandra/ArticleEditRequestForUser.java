package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.ArticleEditBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.List;

@Table("editrequestforuser")
@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequestForUser {

    private String userId;
    private HashMap<String, ArticleEditBean> articleEditBeanMap = new HashMap<>();

}

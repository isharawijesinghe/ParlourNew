package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.requests.ArticleEditDraftRequest;
import com.ss.parlour.articleservice.utils.bean.requests.ArticlePublishEditDraftRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("article_contributors")
@Getter
@Setter
@NoArgsConstructor
public class ArticleContributors {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String contributorId;

    public ArticleContributors(EditDraftArticles editDraftArticles){
        this.articleId = editDraftArticles.getArticleId();
        this.contributorId = editDraftArticles.getEditorId();
    }
}

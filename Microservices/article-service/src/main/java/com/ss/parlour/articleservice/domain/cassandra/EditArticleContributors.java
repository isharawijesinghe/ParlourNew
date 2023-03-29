package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.requests.ArticleEditDraftRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("edit_article_contributors")
@Getter
@Setter
@NoArgsConstructor
public class EditArticleContributors {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String contributorId;

    public EditArticleContributors(ArticleEditDraftRequest.ArticleEditDraftRequestInner articleEditDraftRequestInner){
        this.articleId = articleEditDraftRequestInner.getArticleId();
        this.contributorId = articleEditDraftRequestInner.getEditorId();
    }
}

package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.requests.ArticleEditDraftRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("edit_draft_articles")
@Getter
@Setter
@NoArgsConstructor
public class EditDraftArticles {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String editRequestId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    private String editorId;
    private String authorId;
    private String title;
    private String summary;
    private String content;
    private String categoryId;
    private String thumbnailUrl;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public EditDraftArticles(ArticleEditDraftRequest.ArticleEditDraftRequestInner articleEditDraftRequestInner){
        this.editRequestId = articleEditDraftRequestInner.getEditRequestId();
        this.articleId = articleEditDraftRequestInner.getArticleId();
        this.editorId = articleEditDraftRequestInner.getEditorId();
        this.authorId = articleEditDraftRequestInner.getAuthorId();
        this.title = articleEditDraftRequestInner.getTitle();
        this.summary = articleEditDraftRequestInner.getSummary();
        this.content = articleEditDraftRequestInner.getContent();
        this.categoryId = articleEditDraftRequestInner.getCategoryId();
        this.thumbnailUrl = articleEditDraftRequestInner.getThumbnailUrl();
        this.createdDate = articleEditDraftRequestInner.getCreatedDate();
        this.modifiedDate = articleEditDraftRequestInner.getModifiedDate();
    }

}

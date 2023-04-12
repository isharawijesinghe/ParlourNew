package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Table("shared_articles")
public class SharedArticles {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String editRequestId;
    private String requesterId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String articleId;
    private String status;
    private String ownerId;
    private Timestamp createdDate;

    public SharedArticles(EditRequest editRequest, String status){
        this.editRequestId = editRequest.getEditRequestId();
        this.requesterId = editRequest.getRequesterId();
        this.articleId = editRequest.getArticleId();
        this.status = status;
        this.ownerId = editRequest.getOwnerId();
        this.createdDate = editRequest.getCreatedDate();
    }
}

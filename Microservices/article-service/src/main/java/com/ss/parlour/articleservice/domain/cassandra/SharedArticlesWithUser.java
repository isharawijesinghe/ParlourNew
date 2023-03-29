package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Table("shared_articles_with_user")
public class SharedArticlesWithUser {


    private String editRequestId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String requesterId;
    private String articleId;
    private String status;
    private String ownerId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;

    public SharedArticlesWithUser(EditRequest editRequest, String status){
        this.editRequestId = editRequest.getEditRequestId();
        this.requesterId = editRequest.getRequesterId();
        this.articleId = editRequest.getArticleId();
        this.status = status;
        this.ownerId = editRequest.getOwnerId();
        this.createdDate = editRequest.getCreatedDate();

    }

}

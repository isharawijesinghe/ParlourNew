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

@Table("edit_request_by_user")
@Getter
@Setter
@NoArgsConstructor
public class EditRequestByUser {

    private String editRequestId;
    private String articleId;
    private String title;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String requesterId;
    private String description;
    private String editRequestStatus;
    private String ownerId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public EditRequestByUser(EditRequest editRequest){
        this.editRequestId =  editRequest.getEditRequestId();
        this.articleId = editRequest.getArticleId();
        this.title = editRequest.getTitle();
        this.requesterId = editRequest.getRequesterId();
        this.description = editRequest.getDescription();
        this.editRequestStatus = editRequest.getEditRequestStatus();
        this.ownerId = editRequest.getOwnerId();
        this.createdDate = editRequest.getCreatedDate();
        this.modifiedDate = editRequest.getModifiedDate();
    }

}

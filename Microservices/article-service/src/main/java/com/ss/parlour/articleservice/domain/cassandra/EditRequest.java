package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@NoArgsConstructor
@UserDefinedType
public class EditRequest {

    @PrimaryKey
    private String editRequestId;
    private String articleId;
    private String title;
    private String requesterId;
    private String description;
    private String editRequestStatus;
    private String ownerId;
}

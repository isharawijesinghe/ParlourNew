package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Table("edit_request")
public class EditRequest {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String editRequestId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    private String title;
    private String requesterId;
    private String description;
    private String editRequestStatus;
    private String ownerId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

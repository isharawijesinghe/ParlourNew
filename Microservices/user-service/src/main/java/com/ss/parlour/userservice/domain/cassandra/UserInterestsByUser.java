package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Table("user_interests_by_user")
public class UserInterestsByUser {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String topic;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;

    public UserInterestsByUser(String userId, String topic, Timestamp createdDate){
        this.userId = userId;
        this.topic = topic;
        this.createdDate = createdDate;
    }
}

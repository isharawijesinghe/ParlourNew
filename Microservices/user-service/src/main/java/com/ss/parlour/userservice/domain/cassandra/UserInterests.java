package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("user_interests")
public class UserInterests {

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String userId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String topic;
    private Timestamp createdDate;

    public UserInterests(String userId, String topic, Timestamp createdDate){
        this.userId = userId;
        this.topic = topic;
        this.createdDate = createdDate;
    }
}

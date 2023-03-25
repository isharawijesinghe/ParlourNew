package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("user_token")
public class UserToken {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userIdentification;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String actionType;
    private String userToken;

}

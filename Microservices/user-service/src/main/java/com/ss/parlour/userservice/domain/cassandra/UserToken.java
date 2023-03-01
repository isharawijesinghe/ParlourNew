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
@Table("usertoken")
public class UserToken {

    @PrimaryKeyColumn(name = "username",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String userName;
    @PrimaryKeyColumn(name = "actiontype",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private String actionType;
    private String userToken;

}

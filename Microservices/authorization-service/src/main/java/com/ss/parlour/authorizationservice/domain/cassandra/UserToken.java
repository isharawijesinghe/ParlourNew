package com.ss.parlour.authorizationservice.domain.cassandra;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("usertoken")
public class UserToken {

    @PrimaryKeyColumn(name = "username",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String userName;
    @PrimaryKeyColumn(name = "actiontype",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private String actionType;
    private String userToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

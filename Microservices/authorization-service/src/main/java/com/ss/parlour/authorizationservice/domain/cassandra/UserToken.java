package com.ss.parlour.authorizationservice.domain.cassandra;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("usertoken")
public class UserToken {

    @PrimaryKeyColumn(name = "loginname",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String loginName;
    @PrimaryKeyColumn(name = "actiontype",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private String actionType;
    private String token;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

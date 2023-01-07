package com.ss.parlour.streamservice.domain.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;

@Table("usermappedstream")
public class UserMappedStream {

    @PrimaryKey
    private String userName;
    private Map<String, Stream> userStreamMap = new HashMap<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, Stream> getUserStreamMap() {
        return userStreamMap;
    }

    public void setUserStreamMap(Map<String, Stream> userStreamMap) {
        this.userStreamMap = userStreamMap;
    }
}

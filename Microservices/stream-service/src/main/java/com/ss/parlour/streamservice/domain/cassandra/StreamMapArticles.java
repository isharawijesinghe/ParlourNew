package com.ss.parlour.streamservice.domain.cassandra;

import com.ss.parlour.streamservice.utils.bean.Article;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;

@Table("streammaparticles")
public class StreamMapArticles {

    @PrimaryKey
    private String streamName;
    //@PrimaryKey
    private String userName;
    private Map<String, Article> streamMapArticlesMap = new HashMap();

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Map<String, Article> getStreamMapArticlesMap() {
        return streamMapArticlesMap;
    }

    public void setStreamMapArticlesMap(Map<String, Article> streamMapArticlesMap) {
        this.streamMapArticlesMap = streamMapArticlesMap;
    }
}

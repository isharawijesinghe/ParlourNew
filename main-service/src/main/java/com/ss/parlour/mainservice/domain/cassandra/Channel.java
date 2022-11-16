package com.ss.parlour.mainservice.domain.cassandra;

import com.ss.parlour.mainservice.utils.bean.ArticleBean;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Table("channel")
public class Channel {
    @PrimaryKey
    private Integer channelID;
    private String name;
    private List<ArticleBean> articles;

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

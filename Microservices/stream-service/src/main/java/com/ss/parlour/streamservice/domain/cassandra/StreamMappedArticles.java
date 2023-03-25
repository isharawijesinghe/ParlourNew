package com.ss.parlour.streamservice.domain.cassandra;

import com.ss.parlour.streamservice.utils.bean.Article;
import com.ss.parlour.streamservice.utils.bean.requests.ArticleToStreamRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Table("stream_mapped_articles")
@Getter
@Setter
@NoArgsConstructor
public class StreamMappedArticles {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String streamId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;

    public StreamMappedArticles(ArticleToStreamRequest articleToStreamRequest, String articleId){
        this.streamId = articleToStreamRequest.getStreamId();
        this.articleId = articleId;
        this.createdDate = articleToStreamRequest.getCreatedDate();
    }

}

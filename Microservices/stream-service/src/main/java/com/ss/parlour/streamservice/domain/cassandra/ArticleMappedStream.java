package com.ss.parlour.streamservice.domain.cassandra;

import com.ss.parlour.streamservice.utils.bean.requests.ArticleToStreamRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("article_mapped_stream")
@Getter
@Setter
@NoArgsConstructor
public class ArticleMappedStream {


    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String streamId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String articleId;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;

    public ArticleMappedStream(ArticleToStreamRequest articleToStreamRequest, String articleId){
        this.streamId = articleToStreamRequest.getStreamId();
        this.articleId = articleId;
        this.createdDate = articleToStreamRequest.getCreatedDate();
    }
}

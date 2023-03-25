package com.ss.parlour.streamservice.domain.cassandra;

import com.ss.parlour.streamservice.utils.bean.StreamBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("stream_by_user")
@Getter
@Setter
@NoArgsConstructor
public class StreamByUser {

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String streamId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    private String description;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Timestamp createdDate;

    public StreamByUser(StreamBean streamBean){
        this.streamId = streamBean.getStreamId();
        this.userId = streamBean.getUserId();
        this.description = streamBean.getDescription();
        this.createdDate = streamBean.getCreatedDate();
    }
}

package com.ss.parlour.streamservice.domain.cassandra;

import com.ss.parlour.streamservice.utils.bean.StreamBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("stream")
@Getter
@Setter
@NoArgsConstructor
public class Stream {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String streamId;
    private String userId;
    private String description;
    private Timestamp createdDate;

    public Stream(StreamBean streamBean){
        this.streamId = streamBean.getStreamId();
        this.userId = streamBean.getUserId();
        this.description = streamBean.getDescription();
        this.createdDate = streamBean.getCreatedDate();
    }


}

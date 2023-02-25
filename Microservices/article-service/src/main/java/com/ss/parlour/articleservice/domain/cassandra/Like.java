package com.ss.parlour.articleservice.domain.cassandra;

import com.ss.parlour.articleservice.utils.bean.LikeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("like")
@Getter
@Setter
@NoArgsConstructor
public class Like {

    @PrimaryKey
    private String key;
    private String articleId;
    private String commentId;
    @CassandraType(type = CassandraType.Name.TEXT)
    private LikeType likeType; //Whether like is for article or comment
    private String userName;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}

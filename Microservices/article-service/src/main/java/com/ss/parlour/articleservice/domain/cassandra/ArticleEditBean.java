package com.ss.parlour.articleservice.domain.cassandra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@NoArgsConstructor
@UserDefinedType
@Table("articleeditbean")
public class ArticleEditBean {

    @PrimaryKey
    private String editRequestId;
    private String articleId;
    private String title;
    private String requesterId;
    private String description;
    private String editRequestStatus;
    private String ownerId;
}

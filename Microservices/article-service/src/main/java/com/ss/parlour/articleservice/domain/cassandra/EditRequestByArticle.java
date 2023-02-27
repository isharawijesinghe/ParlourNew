package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("editrequestbyarticle")
@Getter
@Setter
@NoArgsConstructor
public class EditRequestByArticle {

    @PrimaryKey
    private String articleId;
    private HashMap<String, EditRequest> editRequestByArticleMap = new HashMap<>();

    public EditRequestByArticle(String articleId, HashMap<String, EditRequest> editRequestByArticleMap){
        this.articleId = articleId;
        this.editRequestByArticleMap = editRequestByArticleMap;
    }
}

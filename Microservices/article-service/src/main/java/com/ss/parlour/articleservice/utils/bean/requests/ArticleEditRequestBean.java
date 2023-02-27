package com.ss.parlour.articleservice.utils.bean.requests;

import com.ss.parlour.articleservice.domain.cassandra.EditRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequestBean {

    private EditRequest articleEditRequest;

}

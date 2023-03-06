package com.ss.parlour.articleservice.utils.bean.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleListRequest {


    private String loginName;
    private List<String> allowStatusFilter;
    private List<String> removeStatusFilter;

}

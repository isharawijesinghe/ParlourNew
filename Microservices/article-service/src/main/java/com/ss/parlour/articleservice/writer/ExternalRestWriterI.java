package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.response.AuthorDetailResponseBean;

import java.util.Optional;

public interface ExternalRestWriterI {

    Optional<AuthorDetailResponseBean> findAuthorDetailsByLoginName(String loginName);
}

package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.response.AuthorDetailResponse;

import java.util.Optional;

public interface ExternalRestWriterI {

    Optional<AuthorDetailResponse> findAuthorDetailsByLoginName(String loginName);
}

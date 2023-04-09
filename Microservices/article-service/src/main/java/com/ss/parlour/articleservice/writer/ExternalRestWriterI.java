package com.ss.parlour.articleservice.writer;

import com.ss.parlour.articleservice.utils.bean.common.ArticleResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ExternalRestWriterI {

    ResponseEntity<?> findUserInfoDetailsById(String loginName);
}

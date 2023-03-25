package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.utils.bean.ArticleStreamAddaHelperBean;
import com.ss.parlour.streamservice.utils.bean.StreamCreateHelperBean;
import com.ss.parlour.streamservice.utils.bean.StreamDeleteHelperBean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface StreamDAOI {

    void saveStream(StreamCreateHelperBean streamCreateHelperBean);
    void deleteStreamData(StreamDeleteHelperBean streamDeleteHelperBean);
    void saveStreamArticle(ArticleStreamAddaHelperBean articleStreamAddaHelperBean);
    Optional<StreamMappedArticles> findStreamMappedArticles(String streamId, String articleId, Timestamp createdDate);
    Optional<List<StreamMappedArticles>> findListOfStreamMappedArticles(String streamId);
    Optional<StreamByUser> findStreamByUser(String userId, String streamId, Timestamp createdDate);
    Optional<List<StreamByUser>> findListOfStreamByUser(String userId);
    Optional<ArticleMappedStream> findArticleMappedStream(String articleId, String streamId, Timestamp createdDate);
    Optional<List<ArticleMappedStream>> findListOfArticleMappedStream(String articleId);
    Optional<Stream> findStreamData(String streamId);
}

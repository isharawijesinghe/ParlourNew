package com.ss.parlour.streamservice.dao.cassandra;

import com.ss.parlour.streamservice.domain.cassandra.ArticleMappedStream;
import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import com.ss.parlour.streamservice.domain.cassandra.StreamMappedArticles;
import com.ss.parlour.streamservice.repository.cassandra.ArticleMappedStreamRepositoryI;
import com.ss.parlour.streamservice.repository.cassandra.StreamMappedArticlesRepositoryI;
import com.ss.parlour.streamservice.repository.cassandra.StreamRepositoryI;
import com.ss.parlour.streamservice.repository.cassandra.StreamByUserRepositoryI;
import com.ss.parlour.streamservice.utils.bean.ArticleStreamAddaHelperBean;
import com.ss.parlour.streamservice.utils.bean.StreamCreateHelperBean;
import com.ss.parlour.streamservice.utils.bean.StreamDeleteHelperBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class StreamDAO implements StreamDAOI{

    @Autowired
    private StreamRepositoryI streamRepositoryI;

    @Autowired
    private StreamMappedArticlesRepositoryI streamMappedArticlesRepositoryI;

    @Autowired
    private StreamByUserRepositoryI streamByUserRepositoryI;

    @Autowired
    private ArticleMappedStreamRepositoryI articleMappedStreamRepositoryI;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public void saveStream(StreamCreateHelperBean streamCreateHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertStreamDataInBatch(streamCreateHelperBean, batchOps);
        batchOps.execute();
    }

    @Override
    public void deleteStreamData(StreamDeleteHelperBean streamDeleteHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        deleteStreamDataInBatch(streamDeleteHelperBean, batchOps);
        batchOps.execute();
    }

    @Override
    public void saveStreamArticle(ArticleStreamAddaHelperBean articleStreamAddaHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertStreamDataInBatch(articleStreamAddaHelperBean, batchOps);
        batchOps.execute();
    }

    @Override
    public Optional<StreamMappedArticles> findStreamMappedArticles(String streamId, String articleId, Timestamp createdDate){
        return streamMappedArticlesRepositoryI.findByStreamIdAndArticleIdAndCreatedDate(streamId, articleId, createdDate);
    }

    @Override
    public Optional<List<StreamMappedArticles>> findListOfStreamMappedArticles(String streamId){
        return streamMappedArticlesRepositoryI.findByStreamId(streamId);
    }

    @Override
    public Optional<StreamByUser> findStreamByUser(String userId, String streamId, Timestamp createdDate){
        return streamByUserRepositoryI.findByUserIdAndStreamIdAndCreatedDate(userId, streamId, createdDate);
    }

    @Override
    public Optional<List<StreamByUser>> findListOfStreamByUser(String userId){
        return streamByUserRepositoryI.findByUserId(userId);
    }

    @Override
    public Optional<ArticleMappedStream> findArticleMappedStream(String articleId, String streamId, Timestamp createdDate){
        return articleMappedStreamRepositoryI.findByArticleIdAndStreamIdAndCreatedDate(articleId, streamId, createdDate);
    }

    @Override
    public Optional<List<ArticleMappedStream>> findListOfArticleMappedStream(String articleId){
        return articleMappedStreamRepositoryI.findByArticleId(articleId);
    }

    @Override
    public Optional<Stream> findStreamData(String streamId){
        return streamRepositoryI.findByStreamId(streamId);
    }

    protected void insertStreamDataInBatch(StreamCreateHelperBean streamCreateHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(streamCreateHelperBean.getStream());
        batchOps.insert(streamCreateHelperBean.getStreamByUser());
    }


    protected void deleteStreamDataInBatch(StreamDeleteHelperBean streamDeleteHelperBean, CassandraBatchOperations batchOps){
        if (streamDeleteHelperBean.getStream() != null){
            batchOps.delete(streamDeleteHelperBean.getStream());
        }

        if (streamDeleteHelperBean.getStreamByUser() != null){
            batchOps.delete(streamDeleteHelperBean.getStreamByUser());
        }
    }

    protected void insertStreamDataInBatch(ArticleStreamAddaHelperBean articleStreamAddaHelperBean, CassandraBatchOperations batchOps){

        articleStreamAddaHelperBean.getArticleMappedStreamList().stream().forEach(
                ams -> batchOps.insert(ams)
        );

        articleStreamAddaHelperBean.getStreamMappedArticlesList().stream().forEach(
                sma -> batchOps.insert(sma)
        );
    }


}

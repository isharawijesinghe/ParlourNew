package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.repository.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class ArticleDAO implements ArticleDAOI {

    @Autowired
    private ArticleRepositoryI articleRepositoryI;

    @Autowired
    private LikeByArticleRepositoryI likeByArticleRepositoryI;

    @Autowired
    private ArticleHistoryRepositoryI articleHistoryRepositoryI;

    @Autowired
    private EditRequestByUserRepositoryI editRequestByUserRepositoryI;

    @Autowired
    private EditRequestByArticleRepositoryI editRequestByArticleRepositoryI;

    @Autowired
    private SharedArticlesWithUserRepositoryI sharedArticlesWithUserRepositoryI;

    @Autowired
    private SharedArticlesRepositoryI sharedArticlesRepositoryI;

    @Autowired
    private EditRequestRepositoryI editRequestRepositoryI;

    @Autowired
    private EditDraftArticlesRepositoryI editDraftArticlesRepositoryI;

    @Autowired
    private TopicsRepositoryI topicsRepositoryI;

    @Autowired
    private ArticleByUserRepositoryI articleByUserRepositoryI;

    @Autowired
    private LikeByArticleGroupRepositoryI likeByArticleGroupRepositoryI;

    @Autowired
    private  CassandraTemplate cassandraTemplate;


    @Override
    public Optional<Article> getArticleById(String articleId){
        return articleRepositoryI.findArticleByArticleId(articleId);
    }

    @Override
    public Optional<SharedArticles> getSharedArticle(String articleId, String requestId){
        return sharedArticlesRepositoryI.findSharedArticlesByArticleIdAndRequesterId(articleId, requestId);
    }


    @Override
    public Optional<EditRequest> getArticleEditRequest(String editRequestId, String articleId){
        return editRequestRepositoryI.findEditRequestByEditRequestIdAndArticleId(editRequestId, articleId);
    }

    @Override
    public Optional<EditRequestByArticle> getArticleEditRequestForArticleId(String articleId){
        return editRequestByArticleRepositoryI.findById(articleId);
    }

    @Override
    public Optional<EditRequestByUser> getArticleEditRequestForUserId(String userId){
        return editRequestByUserRepositoryI.findById(userId);
    }

    @Override
    public void saveArticleEditRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditRequestInBatch(editRequestHandlerHelperBean, batchOps);
    }

    @Override
    public void saveArticleApprovalRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditApprovalRequestInBatch(editRequestHandlerHelperBean, batchOps);
    }

    @Override
    public void saveArticleEditDraftRequest(EditRequestHandlerHelperBean editRequestHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditDraftRequestInBatch(editRequestHandlerHelperBean, batchOps);
    }

    @Override
    public void saveArticleCreateRequest(ArticleHandlerHelperBean articleHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleCreateRequestInBatch(articleHandlerHelperBean, batchOps);
    }

    @Override
    public void saveTopic(List<Topics> topics){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertTopicsInBatch(topics, batchOps);
    }

    @Override
    public Optional<List<Topics>> loadAllTopicsEntries(){
        return topicsRepositoryI.loadAllTopicsEntries();
    }

    @Override
    public Optional<List<ArticleByUser>> getArticleByUserId(String userId){
        return articleByUserRepositoryI.findByUserId(userId);
    }

    @Override
    public Optional<ArticleByUser> getArticleByUserIdAndArticleId(String userId, String articleId){
        return articleByUserRepositoryI.findByUserIdAndArticleId(userId, articleId);
    }

    @Override
    public void deleteArticleEntry(ArticleHandlerHelperBean articleHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        deleteArticleEntriesInBatch(articleHandlerHelperBean, batchOps);
    }

    @Override
    public Optional<LikeByArticle> getLikeByArticleEntry(String articleId, String userId){
        return likeByArticleRepositoryI.findByArticleIdAndUserId(articleId, userId);
    }

    @Override
    public Optional<LikeByArticleGroup> getLikeByArticleGroupEntry(String articleId, String userId, Timestamp createdDate){
        return likeByArticleGroupRepositoryI.findByArticleIdAndUserIdAndCreatedDate(articleId, userId, createdDate);
    }

    @Override
    public void updateArticleUserLikeRequest(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleUserLikeRequestInBatch(articleLikeHandlerHelperBean, batchOps);
        deleteArticleUserLikeRequestInBatch(articleLikeHandlerHelperBean, batchOps);
    }

    protected void insertArticleEditRequestInBatch(EditRequestHandlerHelperBean editRequestHandlerHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHandlerHelperBean.getEditRequest());
        batchOps.insert(editRequestHandlerHelperBean.getEditRequestByArticle());
        batchOps.insert(editRequestHandlerHelperBean.getEditRequestByUser());
        batchOps.execute();
    }

    protected void insertArticleEditApprovalRequestInBatch(EditRequestHandlerHelperBean editRequestHandlerHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHandlerHelperBean.getEditRequest());
        batchOps.insert(editRequestHandlerHelperBean.getEditRequestByArticle());
        batchOps.insert(editRequestHandlerHelperBean.getEditRequestByUser());
        batchOps.insert(editRequestHandlerHelperBean.getSharedArticles());
        batchOps.insert(editRequestHandlerHelperBean.getSharedArticlesWithUser());
        batchOps.execute();
    }

    protected void insertArticleEditDraftRequestInBatch(EditRequestHandlerHelperBean editRequestHandlerHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHandlerHelperBean.getSharedArticlesWithUser());
        batchOps.insert(editRequestHandlerHelperBean.getEditDraftArticles());
        batchOps.insert(editRequestHandlerHelperBean.getEditArticleContributors());
        batchOps.execute();
    }

    protected void insertArticleCreateRequestInBatch(ArticleHandlerHelperBean articleHandlerHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(articleHandlerHelperBean.getUpdatedArticle());
        batchOps.insert(articleHandlerHelperBean.getArticleByUser());
        if (articleHandlerHelperBean.getArticleHistory() != null){
            //batchOps.insert(articleHandlerHelperBean.getArticleHistory());
        }
        batchOps.execute();
    }

    protected void deleteArticleEntriesInBatch(ArticleHandlerHelperBean articleHandlerHelperBean, CassandraBatchOperations batchOps){
        batchOps.delete(articleHandlerHelperBean.getUpdatedArticle());
        batchOps.delete(articleHandlerHelperBean.getArticleByUser());
        if (articleHandlerHelperBean.getArticleHistory() != null){
            //batchOps.delete(articleHandlerHelperBean.getArticleHistory());
        }
        batchOps.execute();
    }

    protected void insertTopicsInBatch(List<Topics> topics, CassandraBatchOperations batchOps){
        topics.stream().forEach(topic -> batchOps.insert(topics));
        batchOps.execute();
    }

    protected void insertArticleUserLikeRequestInBatch(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean, CassandraBatchOperations batchOps){
        if (articleLikeHandlerHelperBean.getNewLikeByArticle() != null){
            batchOps.insert(articleLikeHandlerHelperBean.getNewLikeByArticle());
        }

        if (articleLikeHandlerHelperBean.getNewLikeByArticleGroup() != null){
            batchOps.insert(articleLikeHandlerHelperBean.getNewLikeByArticleGroup());
        }
    }

    protected void deleteArticleUserLikeRequestInBatch(ArticleLikeHandlerHelperBean articleLikeHandlerHelperBean, CassandraBatchOperations batchOps){
        if (articleLikeHandlerHelperBean.getCurrentLikeByArticle() != null){
            batchOps.delete(articleLikeHandlerHelperBean.getCurrentLikeByArticle());
        }

        if (articleLikeHandlerHelperBean.getCurrentLikeByArticleGroup() != null){
            batchOps.delete(articleLikeHandlerHelperBean.getCurrentLikeByArticleGroup());
        }
    }

}

package com.ss.parlour.articleservice.dao.cassandra;

import com.ss.parlour.articleservice.domain.cassandra.*;
import com.ss.parlour.articleservice.repository.cassandra.*;
import com.ss.parlour.articleservice.utils.bean.ArticleUpdateHelperBean;
import com.ss.parlour.articleservice.utils.bean.EditRequestHelperBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

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
    private  CassandraTemplate cassandraTemplate;

    @Override
    public Optional<Article> getArticleById(String articleId){
        return articleRepositoryI.findById(articleId);
    }

    @Override
    public void saveArticle(Article article){
        articleRepositoryI.save(article);
    }

    @Override
    public Optional<LikeByArticle> getLikeByArticle(String articleId){return likeByArticleRepositoryI.findByArticleId(articleId);}

    @Override
    public void updateArticleHistory(ArticleHistory articleHistory){
        articleHistoryRepositoryI.save(articleHistory);
    }

    @Override
    public Optional<ArticleHistory> getArticleHistoryByArticleId(String articleId){return articleHistoryRepositoryI.findById(articleId);}

    @Override
    public Optional<EditRequest> getArticleEditRequest(String editRequestId){
        return editRequestRepositoryI.findEditRequestByEditRequestId(editRequestId);
    }

    @Override
    public void saveArticleEditRequest(EditRequestByArticle editRequestByArticle){
        editRequestByArticleRepositoryI.save(editRequestByArticle);
    }

    @Override
    public void saveArticleEditRequestForUser(EditRequestByUser articleEditRequest){
        editRequestByUserRepositoryI.save(articleEditRequest);
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
    public void saveSharedArticles(SharedArticles sharedArticles){
        sharedArticlesRepositoryI.save(sharedArticles);
    }

    @Override
    public Optional<SharedArticles> getSharedArticlesForUserId(String userId){
        return sharedArticlesRepositoryI.findById(userId);
    }

    @Override
    public void saveArticleEditRequest(EditRequestHelperBean editRequestHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditRequestInBatch(editRequestHelperBean, batchOps);
    }

    @Override
    public void saveArticleApprovalRequest(EditRequestHelperBean editRequestHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditApprovalRequestInBatch(editRequestHelperBean, batchOps);
    }

    @Override
    public Optional<EditDraftArticles> getEditDraftArticleByArticleId(String articleId){
        return editDraftArticlesRepositoryI.findById(articleId);
    }

    @Override
    public void saveArticleEditDraftRequest(EditRequestHelperBean editRequestHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleEditDraftRequestInBatch(editRequestHelperBean, batchOps);
    }

    @Override
    public void saveArticleCreateRequest(ArticleUpdateHelperBean articleUpdateHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        insertArticleCreateRequestInBatch(articleUpdateHelperBean, batchOps);
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
    public Optional<ArticleByUser> getArticleByUserId(String userId){
        return articleByUserRepositoryI.findById(userId);
    }

    @Override
    public void deleteArticleEntry(ArticleUpdateHelperBean articleUpdateHelperBean){
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        deleteArticleEntriesInBatch(articleUpdateHelperBean, batchOps);
    }

    protected void insertArticleEditRequestInBatch(EditRequestHelperBean editRequestHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHelperBean.getEditRequest());
        batchOps.insert(editRequestHelperBean.getEditRequestByArticle());
        batchOps.insert(editRequestHelperBean.getEditRequestByUser());
        batchOps.execute();
    }

    protected void insertArticleEditApprovalRequestInBatch(EditRequestHelperBean editRequestHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHelperBean.getEditRequest());
        batchOps.insert(editRequestHelperBean.getEditRequestByArticle());
        batchOps.insert(editRequestHelperBean.getEditRequestByUser());
        batchOps.insert(editRequestHelperBean.getSharedArticles());
        batchOps.execute();
    }

    protected void insertArticleEditDraftRequestInBatch(EditRequestHelperBean editRequestHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(editRequestHelperBean.getSharedArticles());
        batchOps.insert(editRequestHelperBean.getEditDraftArticles());
        batchOps.execute();
    }

    protected void insertArticleCreateRequestInBatch(ArticleUpdateHelperBean articleUpdateHelperBean, CassandraBatchOperations batchOps){
        batchOps.insert(articleUpdateHelperBean.getUpdatedArticle());
        batchOps.insert(articleUpdateHelperBean.getArticleByUser());
        if (articleUpdateHelperBean.getArticleHistory() != null){
            batchOps.insert(articleUpdateHelperBean.getArticleHistory());
        }
        batchOps.execute();
    }

    protected void deleteArticleEntriesInBatch(ArticleUpdateHelperBean articleUpdateHelperBean, CassandraBatchOperations batchOps){
        batchOps.delete(articleUpdateHelperBean.getUpdatedArticle());
        batchOps.delete(articleUpdateHelperBean.getArticleByUser());
        if (articleUpdateHelperBean.getArticleHistory() != null){
            batchOps.delete(articleUpdateHelperBean.getArticleHistory());
        }
        batchOps.execute();
    }

    protected void insertTopicsInBatch(List<Topics> topics, CassandraBatchOperations batchOps){
        topics.stream().forEach(topic -> batchOps.insert(topics));
        batchOps.execute();
    }

}

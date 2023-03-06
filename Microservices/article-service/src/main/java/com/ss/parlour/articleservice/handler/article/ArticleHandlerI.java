package com.ss.parlour.articleservice.handler.article;

import com.ss.parlour.articleservice.domain.cassandra.Article;
import com.ss.parlour.articleservice.utils.bean.ArticleBean;
import com.ss.parlour.articleservice.utils.bean.requests.*;
import com.ss.parlour.articleservice.utils.bean.response.*;

import java.util.List;

public interface ArticleHandlerI {

    Article processCreateArticleRequest(ArticleBean articleBean);
    void processDeleteArticleRequest(ArticleDeleteRequest articleDeleteRequest);
    ArticleResponse findArticleById(String articleId);
    List<Article> findArticleByUser(ArticleListRequest articleListRequest);
    ArticleHistoryResponse findArticleHistoryById(ArticleHistoryRequest articleHistoryRequest);
    Article findArticleDetailsById(String articleId);
    ArticleEditRequestResponse processArticleEditRequest(ArticleEditRequest articleEditRequest);
    ArticleEditApproveResponse processArticleEditRequestApproval(ArticleEditApproveRequest articleEditApproveRequest);
    ArticleEditDraftResponse postArticleEditDraft(ArticleEditDraftRequest articleEditDraftRequest);
    TopicAddResponse addTopic(TopicAddRequest topicAddRequest);
    TopicResponse findAllTopic();
}

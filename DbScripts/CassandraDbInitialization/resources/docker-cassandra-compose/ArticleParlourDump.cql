create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;

// ------------------ Table scripts ------------------- //

CREATE TABLE IF NOT EXISTS parlour.article (
    articleId text,
    userId text,
    title text,
    summary text,
    content text,
    status text,
    categoryId text,
    thumbnailUrl text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.article_by_user (
    articleId text,
    userId text,
    title text,
    summary text,
    content text,
    status text,
    categoryId text,
    thumbnailUrl text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY ((userId), articleId)
);

CREATE TABLE IF NOT EXISTS parlour.comment (
    commentId text,
    articleId text,
    parentId text,
    userId text,
    content text,
    createdDate timestamp,
    modifiedDate text,
    PRIMARY KEY ((articleId, commentId))
);

CREATE TABLE IF NOT EXISTS parlour.comment_group (
    commentId text,
    articleId text,
    parentId text,
    userId text,
    content text,
    createdDate timestamp,
    modifiedDate text,
    PRIMARY KEY ((articleId, parentId), createdDate)
)   WITH CLUSTERING ORDER BY (createdDate ASC);

-- CREATE TABLE IF NOT EXISTS parlour.article_history (
--     articleId text,
--     oldArticles frozen<list<article>> ,
--     PRIMARY KEY (articleId)
-- );

CREATE TABLE IF NOT EXISTS parlour.like_by_article (
    articleId text,
    userId text,
    createdDate timestamp,
    PRIMARY KEY ((articleId, userId))
);

CREATE TABLE IF NOT EXISTS parlour.like_by_article_group (
   articleId text,
   userId text,
   createdDate timestamp,
   PRIMARY KEY ((articleId), createdDate, userId )
)  WITH CLUSTERING ORDER BY (createdDate ASC, userId DESC);

CREATE TABLE IF NOT EXISTS parlour.like_by_comment (
    commentId text,
    articleId text,
    userId text,
    createdDate timestamp,
    PRIMARY KEY ((commentId, articleId, userId))
);

CREATE TABLE IF NOT EXISTS parlour.like_by_comment_group (
    commentId text,
    articleId text,
    userId text,
    createdDate timestamp,
    PRIMARY KEY ((commentId, articleId), createdDate, userId )
)  WITH CLUSTERING ORDER BY (createdDate ASC , userId DESC);


CREATE TABLE IF NOT EXISTS parlour.edit_request (
    editRequestId text,
    articleId text,
    title text,
    requesterId text,
    description text,
    editRequestStatus text,
    ownerId text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY ((editRequestId, articleId))
);

CREATE TABLE IF NOT EXISTS parlour.edit_request_by_article (
    editRequestId text,
    articleId text,
    title text,
    requesterId text,
    description text,
    editRequestStatus text,
    ownerId text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY ((articleId), createdDate)
)   WITH CLUSTERING ORDER BY (createdDate ASC);

CREATE TABLE IF NOT EXISTS parlour.edit_request_by_user (
    editRequestId text,
    articleId text,
    title text,
    requesterId text,
    description text,
    editRequestStatus text,
    ownerId text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY ((requesterId), createdDate)
)   WITH CLUSTERING ORDER BY (createdDate ASC);

CREATE TABLE IF NOT EXISTS parlour.shared_articles (
    editRequestId text,
    requesterId text,
    articleId text,
    status text,
    ownerId text,
    createdDate timestamp,
    PRIMARY KEY ((editRequestId, requesterId), createdDate)
);

CREATE TABLE IF NOT EXISTS parlour.shared_articles_with_user (
    editRequestId text,
    requesterId text,
    articleId text,
    status text,
    ownerId text,
    createdDate timestamp,
    PRIMARY KEY ((requesterId), editRequestId, createdDate)
);

CREATE TABLE IF NOT EXISTS parlour.edit_draft_articles (
    editRequestId text,
    editorId text,
    articleId text,
    authorName text,
    title text,
    summary text,
    content text,
    status int,
    categoryId text,
    thumbnailUrl text,
    createdDate timestamp,
    PRIMARY KEY ((editRequestId, editorId), createdDate)
);

CREATE TABLE IF NOT EXISTS parlour.topics (
    name text,
    PRIMARY KEY (name)
);

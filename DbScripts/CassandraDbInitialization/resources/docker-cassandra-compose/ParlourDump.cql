create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;


// ------------------ Type scripts ------------------- //

CREATE TYPE parlour.comment(
    id text,
    articleId text,
    parentId text,
    userName text,
    content text,
    status int,
    createdDate timestamp,
    modifiedDate timestamp,
    likedList set<text> ,
    unLikedList set<text> ,
);

CREATE TYPE parlour.like(
    key text,
    articleId text,
    commentId text,
    likeType text,
    userName text,
    status int,
    createdDate timestamp,
    modifiedDate timestamp
);

CREATE TYPE parlour.article(
    id text,
    userName text,
    title text,
    summary text,
    content text,
    status int,
    categoryId text,
    createdDate timestamp,
    modifiedDate timestamp
);

CREATE TYPE parlour.stream(
    streamId text,
    userName text,
    description text,
    createdDate timestamp
);

CREATE TYPE parlour.edit_request(
    editRequestId text,
    articleId text,
    title text,
    requesterId text,
    description text,
    editRequestStatus text,
    ownerId text
);

CREATE TYPE parlour.shared_articles(
    articleId text,
    status text,
    author text
);


// ------------------ Table scripts ------------------- //

CREATE TABLE IF NOT EXISTS parlour.user (
    loginName text,
    firstName text,
    lastName text,
    email text,
    password text,
    locked boolean,
    enabled boolean,
    createdDate timestamp,
    lastUpdatedDate timestamp,
    activeToken text,
    provider text,
    imageUrl text,
    providerId text,
    PRIMARY KEY (loginName)
);

CREATE TABLE IF NOT EXISTS parlour.user_info (
    loginName text,
    firstName text,
    lastName text,
    country text,
    jobTitle text,
    company text,
    experience text,
    profileImage text,
    description text,
    PRIMARY KEY (loginName)
);

CREATE TABLE IF NOT EXISTS parlour.userLoginNameEmailMapper (
    loginName text,
    email text,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS parlour.userToken (
    userToken text,
    userName text,
    actionType text,
    PRIMARY KEY ((userName, actionType))
);

CREATE TABLE IF NOT EXISTS parlour.article (
    id text,
    userName text,
    title text,
    summary text,
    content text,
    status int,
    categoryId text,
    createdDate timestamp,
    modifiedDate timestamp,
    likedList frozen<set<text>> ,
    unLikedList frozen<set<text>> ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parlour.article_history (
    articleId text,
    oldArticles frozen<list<article>> ,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.comment (
    id text,
    articleId text,
    parentId text,
    userName text,
    content text,
    status int,
    createdDate timestamp,
    modifiedDate text,
    likedList frozen<set<text>> ,
    unLikedList frozen<set<text>> ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parlour.comment_by_article (
    articleId text,
    comments frozen<map<text, list<comment>>> ,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.like_by_article (
    articleId text,
    likes list<frozen<like>> ,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS  parlour.like_by_article (
    articleId text,
    likes list<frozen<like>> ,
    likeMap map<text, frozen<like>>,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.like_by_comment (
    commentId text,
    articleId text,
    likeMap map<text, frozen<like>>,
    PRIMARY KEY ((articleId, commentId))
);

CREATE TABLE IF NOT EXISTS parlour.stream (
    streamId text,
    userName text,
    description text,
    createdDate timestamp,
    PRIMARY KEY (streamId)
);

CREATE TABLE IF NOT EXISTS parlour.streammappedarticles (
    streamId text,
    likeMap map<text, frozen<article>>,
    PRIMARY KEY (streamId)
);

CREATE TABLE IF NOT EXISTS parlour.usermappedstream (
    userName text,
    userStreamMap map<text, frozen<stream>>,
    PRIMARY KEY (userName)
);

CREATE TABLE IF NOT EXISTS parlour.edit_request (
    editRequestId text,
    articleId text,
    title text,
    requesterId text,
    description text,
    editRequestStatus text,
    ownerId text,
    PRIMARY KEY (editRequestId)
);

CREATE TABLE IF NOT EXISTS parlour.edit_request_by_article (
   articleId text,
   editRequestByArticleMap map<text, frozen<edit_request>>,
   PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.edit_request_by_user (
   userId text,
   editRequestByUserMap map<text, frozen<edit_request>>,
   PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS parlour.shared_articles (
   userId text,
   sharedArticleBeanMap map<text, frozen<shared_articles>>,
   PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS parlour.shared_articles (
  articleId text,
  draftArticles map<text, frozen<article>>,
  PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.edit_draft_articles (
  articleId text,
  draftArticles map<text, frozen<article>>,
  PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.topics (
   name text,
   PRIMARY KEY (name)
);
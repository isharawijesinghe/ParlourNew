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
    createdDate timestamp,
    modifiedDate timestamp
    );

CREATE TYPE parlour.stream(
    streamId text,
    userName text,
    description text,
    createdDate timestamp
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

CREATE TABLE IF NOT EXISTS parlour.userLoginNameEmailMapper (
    loginName text,
    email text,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS parlour.userToken (
    token text,
    loginName text,
    actionType text,
    PRIMARY KEY ((loginName, actionType))
);

CREATE TABLE IF NOT EXISTS parlour.article (
    id text,
    userName text,
    title text,
    summary text,
    content text,
    status int,
    createdDate timestamp,
    modifiedDate timestamp,
    likedList frozen<set<text>> ,
    unLikedList frozen<set<text>> ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parlour.articleHistory (
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

CREATE TABLE IF NOT EXISTS parlour.commentByArticle (
    articleId text,
    comments frozen<map<text, list<comment>>> ,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.likeByArticle (
    articleId text,
    likes list<frozen<like>> ,
    PRIMARY KEY (articleId)
);

CREATE TABLEIF NOT EXISTS  parlour.likeByArticle (
    articleId text,
    likes list<frozen<like>> ,
    likeMap map<text, frozen<like>>,
    PRIMARY KEY (articleId)
);

CREATE TABLE IF NOT EXISTS parlour.likeByComment (
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
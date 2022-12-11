create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;


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

-- CREATE TABLE IF NOT EXISTS parlour.userToken (
--     token text,
--     loginName text,
--     type text,
--     PRIMARY KEY (token)
-- );

CREATE TABLE parlour.channel (
    name text,
    title text,
    ownerName text,
    createdDate timestamp,
    PRIMARY KEY (name)
);

CREATE TABLE parlour.article (
    id text,
    name text,
    title text,
    content text,
    author text,
    createdDate timestamp,
    modifiedDate timestamp,
    PRIMARY KEY (name)
);

CREATE TABLE parlour.channelArticles (
    channelNam text,
    articleName text,
    PRIMARY KEY (channelNam,articleName)
);

CREATE TABLE parlour.articleHistory (
    name text,
    titleNew text,
    titlePrev text,
    contentNew text,
    contentPrev text,
    createdDate timestamp,
    editedBy text,
    PRIMARY KEY (name,editedBy,createdDate)
);

CREATE TYPE parlour.comment(
    id text,
    articleId text,
    parentId text,
    authorName text,
    content text,
    status int,
    createdDate timestamp,
    modifiedDate timestamp
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

CREATE TABLE parlour.comment (
    id text,
    articleId text,
    parentId text,
    authorName text,
    content text,
    status int,
    createdDate timestamp,
    modifiedDate text,
    PRIMARY KEY (id)
);

CREATE TABLE parlour.commentbyarticle (
    articleid text,
    comments list<frozen<comment>> ,
    PRIMARY KEY (articleid)
);

CREATE TABLE parlour.commentbyarticle (
    articleid text,
    likes list<frozen<like>> ,
    PRIMARY KEY (articleid)
);

CREATE TABLE parlour.likebyarticle (
    articleid text,
    likes list<frozen<like>> ,
    likeMap map<text, frozen<like>>,
    PRIMARY KEY (articleid)
);

CREATE TABLE parlour.likebycomment (
    commentid text,
    articleid text,
    likes list<frozen<like>> ,
    likeMap map<text, frozen<like>>,
    PRIMARY KEY (articleid)
);

-- INSERT INTO parlour.user (loginName,
--                           firstName ,
--                           lastName ,
--                           email ,
--                           password ,
--                           locked ,
--                           enabled ,
--                           createdDate,
--                           lastUpdatedDate,
--                           activeToken)
-- VALUES
--        ('user1',
--         'user1First',
--         'user1Last',
--         'user1@gmail.com',
--         '$2a$11$cEANSCawfJ7Si2K6fiEJMOMgFBeBFq3HVgZrm.XhHCPzqvgC0ztJi',
--         false,
--         true,
--         '2022-02-15',
--         '2022-02-15',
--         '');

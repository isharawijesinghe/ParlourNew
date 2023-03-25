create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;

// ------------------ Table scripts ------------------- //

CREATE TABLE IF NOT EXISTS parlour.stream (
    streamId text,
    userId text,
    description text,
    createdDate timestamp,
    PRIMARY KEY (streamId)
);

CREATE TABLE IF NOT EXISTS parlour.stream_by_user (
    streamId text,
    userId text,
    description text,
    createdDate timestamp,
    PRIMARY KEY ((userId), streamId, createdDate)
);

CREATE TABLE IF NOT EXISTS parlour.stream_mapped_articles (
    streamId text,
    articleId text,
    createdDate timestamp,
    PRIMARY KEY ((streamId), articleId, createdDate)
);

CREATE TABLE IF NOT EXISTS parlour.article_mapped_stream (
    streamId text,
    articleId text,
    createdDate timestamp,
    PRIMARY KEY ((articleId), streamId, createdDate)
);


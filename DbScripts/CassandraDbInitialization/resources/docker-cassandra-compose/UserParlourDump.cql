create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;

// ------------------ Table scripts ------------------- //

CREATE TABLE IF NOT EXISTS parlour.user (
    userId text,
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
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS parlour.user_login_name_mapper (
    loginName text,
    userId text,
    PRIMARY KEY (loginName)
);

CREATE TABLE IF NOT EXISTS parlour.user_login_email_mapper (
    email text,
    userId text,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS parlour.user_info (
    userId text,
    loginName text,
    firstName text,
    lastName text,
    country text,
    jobTitle text,
    company text,
    experience text,
    profileImage text,
    description text,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS parlour.user_token (
    userToken text,
    userIdentification text,
    actionType text,
    PRIMARY KEY ((userIdentification), actionType)
);

CREATE TABLE IF NOT EXISTS parlour.user_interests (
    userId text,
    topic text ,
    createdDate timestamp,
    PRIMARY KEY ((topic), userId)
);

CREATE TABLE IF NOT EXISTS parlour.user_interests_by_user (
    userId text,
    topic text,
    createdDate timestamp,
    PRIMARY KEY ((userId), topic, createdDate)
);
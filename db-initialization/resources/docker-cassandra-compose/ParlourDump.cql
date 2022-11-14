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
    PRIMARY KEY (loginName)
);

CREATE TABLE IF NOT EXISTS parlour.userLoginNameEmailMapper (
    loginName text,
    email text,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS parlour.userToken (
    loginName text,
    type text,
    token text,
    PRIMARY KEY (token)
);


INSERT INTO parlour.user (loginName,
                          firstName ,
                          lastName ,
                          email ,
                          password ,
                          locked ,
                          enabled ,
                          createdDate,
                          lastUpdatedDate,
                          activeToken)
VALUES
       ('user1',
        'user1First',
        'user1Last',
        'user1@gmail.com',
        '$2a$11$cEANSCawfJ7Si2K6fiEJMOMgFBeBFq3HVgZrm.XhHCPzqvgC0ztJi',
        false,
        true,
        '2022-02-15',
        '2022-02-15',
        '');

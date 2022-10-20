create keyspace IF NOT EXISTS parlour with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1}
and durable_writes = true;


CREATE TABLE parlour.user (
    loginName text,
    address text,
    createdDate timestamp,
    defaultEmail text,
    defaultMobile text,
    firstName text,
    gender int,
    loginPw text,
    otherEmails text,
    otherMobiles text,
    otherNames text,
    secondName text,
    title text,
    PRIMARY KEY (loginname)
);

INSERT INTO parlour.user (loginName,
                          address ,
                          createdDate ,
                          defaultEmail ,
                          defaultMobile ,
                          firstName ,
                          gender ,
                          loginPw ,
                          otherEmails,
                          otherMobiles ,
                          otherNames ,
                          secondName ,
                          title )
VALUES
       ('user1',
        'colombo',
        '2022-02-15',
        'user1@gmail.com',
        '0710935827',
        'user1First',
        1,
        '$2a$11$cEANSCawfJ7Si2K6fiEJMOMgFBeBFq3HVgZrm.XhHCPzqvgC0ztJi',
        'user1other@gmail.com',
        '071095829',
        'user1other',
        'user1second',
        'Engineer');

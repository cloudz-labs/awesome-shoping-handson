-- accounts
DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
  id bigint NOT NULL AUTO_INCREMENT
  ,username varchar(255)  NOT NULL
  ,name varchar(255)  NOT NULL
  ,password varchar(255)  NOT NULL
  ,mobile varchar(255)  NOT NULL
  ,address varchar(255)  NOT NULL
  ,scope varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (id)
);

-- account_events
DROP TABLE IF EXISTS account_events;
CREATE TABLE account_events (
  id bigint NOT NULL
  ,domain varchar(255) NOT NULL
  ,accountId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload json NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt varchar(255) NOT NULL
  ,PRIMARY KEY (id)
);
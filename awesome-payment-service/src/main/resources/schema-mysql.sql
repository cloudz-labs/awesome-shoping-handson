-- products
DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
  id bigint NOT NULL AUTO_INCREMENT
  ,accountId bigint NOT NULL
  ,orderId bigint NOT NULL
  ,paymentMethod varchar(255)  NOT NULL
  ,paymentDetail1 varchar(255)  
  ,paymentDetail2 varchar(255) 
  ,paymentDetail3 varchar(255)
  ,price bigint  NOT NULL
  ,paid varchar(255)  NOT NULL
  ,active varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- payment_events
DROP TABLE IF EXISTS payment_events;
CREATE TABLE payment_events (
  id bigint NOT NULL 
  ,domain varchar(255) NOT NULL
  ,paymentId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload json NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- account_banks
DROP TABLE IF EXISTS account_banks;
CREATE TABLE account_banks (
  id bigint NOT NULL AUTO_INCREMENT
  ,accountId bigint NOT NULL
  ,bankName varchar(255) NOT NULL
  ,bankNumber varchar(255) NOT NULL
  ,active varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);

-- account_bank_events
DROP TABLE IF EXISTS account_bank_events;
CREATE TABLE account_bank_events (
  id bigint NOT NULL 
  ,domain varchar(255) NOT NULL
  ,accountBankId bigint  NOT NULL
  ,eventType varchar(255)  NOT NULL
  ,payload json NOT NULL
  ,txId varchar(255)  NOT NULL
  ,createdAt datetime NOT NULL
  ,PRIMARY KEY (`id`)
);
INSERT INTO accounts 
(id, username, name, password, mobile, address, scope, createdAt)
VALUES
(1, 'lpeterson2a@google.co.uk','하이','et4kBkLz2Kd','01012345678','경기도 성남시 분당구','customer', '20180908181344');
  
INSERT INTO account_events 
(id, domain, accountId, eventType, payload, txId, createdAt)
VALUES
(1, "account", 1, 'AccountCreated'
, '{
	"id": 1
	, "username": "lpeterson2a@google.co.uk"
	, "name": "하이"
	, "mobile": "01012345678"
	, "address": "경기도 성남시 분당구"
	, "scope": "customer"
   }'
, 'tx123123123'
, '20180908181344');

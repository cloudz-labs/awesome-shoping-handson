INSERT INTO payments
(accountId,orderId,paymentMethod,paymentDetail1,paymentDetail2,paymentDetail3,price,paid,active,createdAt)
VALUES
(1,1,'bank','kb','123123123123','',90000,'paid','active',NOW());

INSERT INTO payment_events
(id,paymentId,domain, EventType,payload,txId,createdAt)
VALUES
(1,1,'payment','PaymentCreated'
,'{"id":1,"accountId":1,"orderId":1,"paymentMethod":"bank","paymentDetail1":"kb","paymentDetail2":"123123123123","paymentDetail3":"","price":90000,"paid":"paid","active":"active"}'
,'tx123123128'
,NOW());

INSERT INTO account_banks
(accountId,bankName,bankNumber,active,createdAt)
VALUES
(1,'kb','123123123123',"active",NOW());

INSERT INTO account_bank_events
(id,accountBankId,domain,eventType,payload,txId,createdAt)
VALUES
(1,1,'accountBank','AccountBankCreated'
,'{"id":1,"accountId":1,"bankName":"kb","bankNumber":"123123123123","active":"active"}'
,'tx123123127'
,NOW());
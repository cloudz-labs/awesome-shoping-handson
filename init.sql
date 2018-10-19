

create table sequence(
    id long not null
    ,seq_name varchar(40) not null
);

insert into sequence(id, seq_name) values(1,'eventId');
//awesome-order-service만
//insert into sequence(id, seq_name) values(1,'orderId');

select get_Sequence('eventId');
//awesome-order-service만
//select get_Sequence('orderId');

set sql_safe_updates=0;

delimiter $$
create function get_sequence(p_seq_name varchar(40))
returns long reads sql data
begin
declare
result_id long;
update sequence set id = LAST_INSERT_ID(id+1) where seq_name = p_seq_name;
set result_id = (select LAST_INSERT_ID());
return result_id;
end $$ delimiter;

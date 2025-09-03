alter table cp_students add constellation varchar2(20);

insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
select seq_se_resources.nextval,'/mobile/collect/dorm-bed-mobile','/mobile/collect/dorm-bed-mobile',1,1,0 from dual;
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
select seq_se_resources.nextval,'/mobile/statistics/wap-statistics','/mobile/statistics/wap-statistics',1,1,0 from dual;
ALTER TABLE U_ZZCX_YXXT.YXW_FINANCE_GREEN_STDS ADD YEAR VARCHAR2(100);
ALTER TABLE U_ZZCX_YXXT.YXW_FINANCE_GREEN_STDS ADD CREATE_TIME TIMESTAMP;

--resource.scope 0：公开 1：公有 2：私有
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/statistics/finance-green-statistics','财务统计',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
  values ((select max(id) from se_resources where name = '/statistics/finance-green-statistics'), 3);
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/statistics/department-student-statistics','院系统计',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
  values ((select max(id) from se_resources where name = '/statistics/department-student-statistics'), 3);
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/statistics/student-statistics','全校迎新统计',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
  values ((select max(id) from se_resources where name = '/statistics/student-statistics'), 3);
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/statistics/dorm-statistics','宿舍统计',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
  values ((select max(id) from se_resources where name = '/statistics/dorm-statistics'), 3);
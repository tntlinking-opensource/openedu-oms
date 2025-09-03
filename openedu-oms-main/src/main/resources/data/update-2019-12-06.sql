--resource.scope 0：公开 1：公有 2：私有
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
    select seq_se_resources.nextval,'/prepare/student-admin-manager','招办学生管理',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
    values ((select max(id) from se_resources where name = '/prepare/student-admin-manager'), 3);
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
    select seq_se_resources.nextval,'/prepare/student-department-manager','院系学生管理',1,2,0 from dual;
insert into se_resources_categories (resource_id, category_id)
    values ((select max(id) from se_resources where name = '/prepare/student-department-manager'), 3);
--resource.scope 0：公开 1：公有 2：私有
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
    select seq_se_resources.nextval,'/msg/message','消息接口',1,1,0 from dual;
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
    select seq_se_resources.nextval,'/mobile/msg/student-of-same-city','移动端同城',1,1,0 from dual;
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
    select seq_se_resources.nextval,'/mobile/msg/wap-message','移动端消息',1,1,0 from dual;
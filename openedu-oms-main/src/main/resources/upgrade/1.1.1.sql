alter table YXW_SUPPLIES_STDS add agree NUMBER(1);
alter table YXW_SUPPLIESES add num NUMBER(10);
alter table YXW_SUPPLIESES add remark VARCHAR2(300 CHAR);

update YXW_SUPPLIESES set num = 1;
update YXW_SUPPLIES_STDS set agree = 1;

insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/web/collect/supplies-info2', '生活用品填写2', 1, 1, 0);




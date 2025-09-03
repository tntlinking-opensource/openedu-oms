insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/web/collect/supplies/supplies-collect', '生活用品填写', 1, 1, 0);
insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/web/collect/supplies/supplies-collect-mobile', '生活用品填写-移动端', 1, 1, 0);
  insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/web/collect/clothes/clothes-collect-mobile', '服装尺码填写-移动端', 1, 1, 0);
update se_resources set name = '/web/collect/supplies/supplies-setting' where name = '/web/collect/supplies-setting';
update se_resources set name = '/web/collect/supplies/supplies-manager' where name = '/web/collect/supplies-manager';
update se_menus set entry = '/web/collect/supplies/supplies-setting' where entry = '/web/collect/supplies-setting';
update se_menus set entry = '/web/collect/supplies/supplies-manager' where entry = '/web/collect/supplies-manager';

insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/web/index2', '首页2', 0, 1, 0);

alter table LC_PROCESS_LINKS add settingable NUMBER(1) default 0;

insert into SE_RESOURCES
  (id, name, title, scope, enabled, need_params)
values
  (seq_se_resources.nextval, '/common/webuploader2', '图片上传', 1, 1, 0);
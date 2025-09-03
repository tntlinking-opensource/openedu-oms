insert into sys_dict_types(id, code, name, enabled) select seq_sys_dict_types.nextval, 'DORM_CHANGE_TYPE', '宿舍变动类型', 1 from dual;
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_01_XSFP', '新生分配',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_02_SGTZ', '宿管调整',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_03_BGSQ', '变更申请',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_04_TS', '退宿',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_05_LX', '留校',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
 insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_01', '新生分配',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_02', '宿管调整',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_03', '变更申请',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_04', '退宿',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_CHANGE_TYPE_05', '留校',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_CHANGE_TYPE'));

insert into sys_dict_types(id, code, name, enabled) select seq_sys_dict_types.nextval, 'DORM_ROOM_DIRECTION', '宿舍朝向', 1 from dual;
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_ROOM_DIRECTION_01', '南',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_ROOM_DIRECTION'));
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_ROOM_DIRECTION_02', '北',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_ROOM_DIRECTION'));

insert into sys_dict_types(id, code, name, enabled) select seq_sys_dict_types.nextval, 'DORM_ROOM_BED_NUM', '床位数', 1 from dual;
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_ROOM_BED_NUM_01', '4',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_ROOM_BED_NUM'));

insert into sys_dict_types(id, code, name, enabled) select seq_sys_dict_types.nextval, 'DORM_ROOM_TYPE', '宿舍类型（用途）', 1 from dual;
insert into sys_dict_datas(id, code, name, enabled, dict_type_id)
 values(seq_sys_dict_datas.nextval, 'DORM_ROOM_TYPE_01', '学生',1, (select max(id) from sys_dict_types dt where dt.code = 'DORM_ROOM_TYPE'));

--resource.scope 0：公开 1：公有 2：私有
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/web/collect/dorm-bed','床位选择',1,1,0 from dual;
insert into SE_RESOURCES(id,name,title,enabled,SCOPE,NEED_PARAMS)
  select seq_se_resources.nextval,'/mobile/collect/dorm-bed-mobile','床位选择移动端',1,1,0 from dual;
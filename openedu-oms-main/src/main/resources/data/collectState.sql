--到站登记
insert into YXW_COLLECT_SWITCH_STATES
  (id, finished, collect_switch_id, User_Id, student_id, create_time)
  select SEQ_YXW_COLLECT_SWITCH_STATES.nextval,
         1,
         (select max(id) from yxw_collect_switches where code = '02'),
         s.user_id user_id,
         s.id student_id,
         sysdate
    from yxw_station_students sp
    join cp_students s
      on sp.student_id = s.id;
--军训服装
        insert into YXW_COLLECT_SWITCH_STATES
  (id, finished, collect_switch_id, User_Id, student_id, create_time)
  select SEQ_YXW_COLLECT_SWITCH_STATES.nextval,
         1,
         (select max(id) from yxw_collect_switches where code = '03'),
         s.user_id user_id,
         s.id student_id,
         sysdate
    from YXW_CLOTHES_STUDENTS sp
    join cp_students s
      on sp.student_id = s.id;
--生活用品
insert into YXW_COLLECT_SWITCH_STATES
  (id, finished, collect_switch_id, User_Id, student_id, create_time)
  select SEQ_YXW_COLLECT_SWITCH_STATES.nextval,
         1,
         (select max(id) from yxw_collect_switches where code = '04'),
         s.user_id user_id,
         s.id student_id,
         sysdate
    from yxw_clothes_students sp
    join cp_students s
      on sp.student_id = s.id;
--红色通道
insert into YXW_COLLECT_SWITCH_STATES
  (id, finished, collect_switch_id, User_Id, student_id, create_time)
  select SEQ_YXW_COLLECT_SWITCH_STATES.nextval,
         1,
         (select max(id) from yxw_collect_switches where code = '05'),
         s.user_id user_id,
         s.id student_id,
         sysdate
    from yxw_finance_green_stds sp
    join cp_students s
      on sp.student_id = s.id;
--自选专业
insert into YXW_COLLECT_SWITCH_STATES
  (id, finished, collect_switch_id, User_Id, student_id, create_time)
  select SEQ_YXW_COLLECT_SWITCH_STATES.nextval,
         1,
         (select max(id) from yxw_collect_switches where code = '06'),
         s.user_id user_id,
         s.id student_id,
         sysdate
    from yxp_student_majors sm
    join yxp_student_prepares sp
      on sm.student_id = sp.id
    join cp_students s
      on sp.code = s.code;

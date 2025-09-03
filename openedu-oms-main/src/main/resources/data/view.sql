create or replace view lc_item_log_statuses as
select (t.student_id || '@' || t.item_id) id,
       t.student_id,
       t.batch_id,
       t.item_id,
       l.id log_id,
       l.enabled
  from (select s.id student_id, t.batch_id, t.item_id
          from cp_students s
          left join (select b.id            batch_id,
                           i.id            item_id,
                           be.dict_data_id education_id,
                           b.year year_
                      from lc_process_batches b
                      join lc_processes p
                        on b.process_id = p.id
                      join lc_process_link_items i
                        on i.process_id = p.id
                      join lc_process_batches_educations be
                        on b.id = be.process_batch_id) t
            on s.education_id = t.education_id
            and s.grade = t.year_
            where s.registed = 1
         order by student_id) t
  left join lc_process_link_item_logs l
    on t.student_id = l.student_id
   and t.item_id = l.item_id
 order by t.student_id;


create or replace view ss_student_beds as
select s.id, s.id student_id, b.id bed_id
     from cp_students s
     left join ss_dorm_beds b
     on s.id = b.student_id;

create or replace view yxd_bed_students as
select b.id bed_id, s.id student_id
  from cp_students s
  left join yxd_beds b
    on s.id = b.student_id;

create or replace view yxd_student_beds as
select s.id, s.id student_id, b.id bed_id
     from cp_students s
     left join yxd_beds b
     on s.id = b.student_id;

create or replace view yxp_student_leave_views as
select S.ID,
        S.ID STUDENT_ID,
        SL.ID LEAVE_ID
   from CP_STUDENTS S
   LEFT JOIN YXP_STUDENT_LEAVES SL
     ON S.ID = SL.STUDENT_ID;

create or replace view yxp_student_print_views as
select s.id,
       s.id student_id,
       p.id print_id,
       case
         when p.print_notice is null then
          0
         else
          p.print_notice
       end print_notice,
       case
         when p.print_face is null then
          0
         else
          p.print_face
       end print_face
  from cp_students s
  join yxp_student_prepares sp
    on s.code = sp.code
  left join yxp_student_prints p
    on s.id = p.student_id
 where sp.enrolled = 1;

create or replace view yxp_view_tests as
select "ID","ADDRESS","EMAIL","HOUSEHOLD_ADDRESS","PHONE","TELEPHONE","ZIPCODE"
    from yxp_student_contacts;

create or replace view yxs_item_log_statuses as
select (t.student_id || '@' || t.item_id) id,
       t.student_id,
       t.batch_id,
       t.item_id,
       l.id log_id,
       l.enabled
  from (select s.id student_id, t.batch_id, t.item_id
          from cp_students s,
               (select b.id batch_id, i.id item_id
                  from lc_process_batches b
                  join lc_processes p
                    on b.process_id = p.id
                  join lc_process_link_items i
                    on i.process_id = p.id) t
         order by student_id) t
  left join lc_process_link_item_logs l
    on t.student_id = l.student_id and t.item_id = l.item_id
 order by t.student_id;

-- 采集状态
create or replace view yxw_collect_switch_state_views as
select s.id || '@' || cs.id as id,
       css.id state_id,
       s.id student_id,
       cs.id COLLECT_SWITCH_ID
  from yxw_collect_switches cs
 cross join cp_students s
  left join YXW_COLLECT_SWITCH_STATES css
    on s.id = css.student_id
   and cs.id = css.collect_switch_id;

-- 未报到学生
create or replace view YXW_WELCOME_STUDENT_VIEWS as
select s.id, s.id student_id, ws.registered, ws.batch_id, ws.reason_id,ws.remark
  from CP_STUDENTS S
  left join YXW_WELCOME_STUDENTS ws
    on s.id = ws.student_id;
    
--学生住宿标准
create OR REPLACE FORCE VIEW V_ZSBZ
as
select s.id,s.code,s.name,s.cardcode,fsi.fee_total from cp_students s
left join yxf_finance_students fs on s.id = fs.student_id
left join yxf_finance_student_items fsi on fs.id = fsi.finance_student_id
left join yxf_finance_items fi on fsi.item_id = fi.id
where s.code is not null and fi.code = '003';


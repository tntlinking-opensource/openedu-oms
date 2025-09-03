-- 2019-10-10
CREATE OR REPLACE VIEW U_ZZCX_YXXT.LC_ITEM_LOG_STATUSES
(ID,STUDENT_ID,BATCH_ID,ITEM_ID,LOG_ID,ENABLED)
AS
select (t.student_id || '@' || t.item_id) id,
       t.student_id,
       t.batch_id,
       t.item_id,
       l.id log_id,
       l.enabled
  from (select s.id student_id, t.batch_id, t.item_id
          from cp_students s
          left join (select b.id            batch_id,
                            b."YEAR",
                           i.id            item_id,
                           be.dict_data_id education_id
                      from lc_process_batches b
                      join lc_processes p
                        on b.process_id = p.id
                      join lc_process_link_items i
                        on i.process_id = p.id
                      join lc_process_batches_educations be
                        on b.id = be.process_batch_id) t
            on s.education_id = t.education_id
            AND s.GRADE = t."YEAR"
         order by student_id) t
  left join lc_process_link_item_logs l
    on t.student_id = l.student_id
   and t.item_id = l.item_id
 order by t.student_id;

-- V1.0.1
-- Add/modify columns
alter table YXP_STUDENT_PREPARES rename column area_id to COUNTY_ID;
-- Create/Recreate primary, unique and foreign key constraints
alter table YXP_STUDENT_PREPARES
  drop constraint FKA78A41067F1FA5D6;
alter table YXP_STUDENT_PREPARES
  add constraint FKA78A41067F1FA5D6 foreign key (COUNTY_ID)
  references SYS_DICT_DATAS (ID);

--添加字段
alter table yxw_welcome_students add(
	remark varchar2(300),
)
  
alter table cp_students add(
notice varchar2(100),
noticed number(1) default 0
)

alter table cp_admin_classes add(
num number(3) default 0
)

--生活用品添加是否领用字段
alter table yxw_supplies_stds add(
used number(1) default 0
)

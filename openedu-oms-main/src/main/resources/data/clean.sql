update CP_STUDENTS set contact_id = null, home_id = null, info_id = null, other_id = null;
delete from CPS_STD_FAMILIES;
delete from CPS_STD_HOMES;
delete from CP_STUDENT_CONTACTS;
delete from CP_STUDENT_EDUCATIONS;
delete from CP_STUDENT_FAMILIES;
delete from CP_STUDENT_HOMES;
delete from CP_STUDENT_INFOES;
delete from CP_STUDENT_OTHERS;
delete from LC_PROCESS_LINK_ITEM_LOGS;
delete from YXP_STUDENT_ENROLLS;
--宿舍
delete from SS_BULIDBED_CHANGES;
delete from SS_DORM_BED_LOGS;
delete from SS_BED_EXCHANGES_DBS;
delete from SS_BED_EXCHANGES_EX_DBS;
delete from SS_DORM_CHANGE_APPLIES;
delete from SS_DORM_CHANGE_LOGS;
delete from SS_DORM_STUDENTS;
delete from SS_DORM_PLAN_BEDS;
delete from SS_DORM_SCHOOL_STDS;
delete from SS_DORM_STUDENT_BEDS;
delete from SS_DORM_BEDS;
delete from SS_DORM_VIOLATES_DIS_STDS;
delete from SS_DORM_VISITORS;
delete from SS_DORM_CHOOSE_LOGGERS;
delete from YXF_FINANCE_GREEN_SITEMS;
delete from YXF_FINANCE_GREEN_STUDENTS;
delete from YXF_FINANCE_ORDER_ITEMS;
delete from YXF_FINANCE_ORDERS;
delete from YXF_FINANCE_STUDENT_ITEMS;
delete from YXF_FINANCE_STUDENTS;
delete from YXP_STUDENT_LEAVES;
delete from YXP_STUDENT_PRINTS;
delete from YXS_WELCOME_STUDENTS;
delete from YXW_CLOTHES_STUDENTS;
delete from YXW_COLLECT_SWITCH_STATES;
delete from YXW_FINANCE_GREEN_STD_ITEMS;
delete from YXW_FINANCE_GREEN_STDS;
delete from YXW_STATION_STUDENTS;
delete from YXW_SUPPLIES_STD_ITEMS;
delete from YXW_SUPPLIES_STDS;
delete from YXW_WELCOME_STUDENTS;
delete from LC_PROCESS_PLI_FORM_VALS;
delete from YXP_STUDENT_MAJOR_DETAILS;
delete from YXP_STUDENT_MAJORS;
delete from YXW_COLLECT_STATE_SI;
delete from YXWC_CLOTHES_STUDENT_SIZES;
delete from YXWC_CLOTHES_STUDENTS;
delete from YXP_STUDENT_CLASSES;
delete from yxp_student_prepares;
delete from CP_STUDENTS;
delete from sys_business_logs;
delete from SE_SESSIONINFO_LOGS;
delete from SE_SESSIONINFOES;
update se_users set enabled = 1;
update se_users set enabled = 0 where id not in (1, 166759) and id not in (
  select user_id from cp_teachers
);
delete from SE_USERS_CATEGORIES where user_id not in (
  select id from se_users where enabled = 1
);
delete from SE_GROUP_MEMBERS where user_id not in (
  select id from se_users where enabled = 1
);
delete from YXW_MSG_GROUP_MEMBERS where user_id not in (
  select id from se_users where enabled = 1
);
delete from YXW_MSG_GROUP_MSGS where user_id not in (
  select id from se_users where enabled = 1
);
delete from YXW_ASK_STUDENTS where student_id not in (
  select id from se_users where enabled = 1
);
delete from se_users where enabled = 0;